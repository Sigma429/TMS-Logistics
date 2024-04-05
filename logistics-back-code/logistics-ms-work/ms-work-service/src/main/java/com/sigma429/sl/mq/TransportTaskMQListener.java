package com.sigma429.sl.mq;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.sigma429.sl.DriverJobFeign;
import com.sigma429.sl.TransportLineFeign;
import com.sigma429.sl.constant.Constants;
import com.sigma429.sl.domain.TransportLineDTO;
import com.sigma429.sl.domain.TransportLineSearchDTO;
import com.sigma429.sl.entity.TransportOrderEntity;
import com.sigma429.sl.entity.TransportOrderTaskEntity;
import com.sigma429.sl.entity.TransportTaskEntity;
import com.sigma429.sl.enums.transportorder.TransportOrderSchedulingStatus;
import com.sigma429.sl.enums.transporttask.TransportTaskAssignedStatus;
import com.sigma429.sl.enums.transporttask.TransportTaskLoadingStatus;
import com.sigma429.sl.enums.transporttask.TransportTaskStatus;
import com.sigma429.sl.service.TransportOrderService;
import com.sigma429.sl.service.TransportOrderTaskService;
import com.sigma429.sl.service.TransportTaskService;
import com.sigma429.sl.truck.TruckPlanDto;
import com.sigma429.sl.truck.TruckPlanFeign;
import com.sigma429.sl.util.ObjectUtil;
import com.sigma429.sl.util.PageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName:TransportTaskMQListener
 * Package:com.sigma429.sl.mq
 * Description:
 * @Author:14亿少女的梦-Sigma429
 * @Create:2024/04/05 - 15:46
 * @Version:v1.0
 */
@Component
@Slf4j
public class TransportTaskMQListener {
    @Resource
    private DriverJobFeign driverJobFeign;
    @Resource
    private TruckPlanFeign truckPlanFeign;
    @Resource
    private TransportLineFeign transportLineFeign;
    @Resource
    private TransportTaskService transportTaskService;
    @Resource
    private TransportOrderTaskService transportOrderTaskService;
    @Resource
    private TransportOrderService transportOrderService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = Constants.MQ.Queues.WORK_TRANSPORT_TASK_CREATE),
            exchange = @Exchange(name = Constants.MQ.Exchanges.TRANSPORT_TASK, type = ExchangeTypes.TOPIC),
            key = Constants.MQ.RoutingKeys.TRANSPORT_TASK_CREATE
    ))
    public void listenTransportTaskMsg(String msg) {
        // 解析消息 {"driverIds":[123,345], "truckPlanId":456, "truckId":1210114964812075008,"totalVolume":4.2,
        // "endOrganId":90001,"totalWeight":7,"transportOrderIdList":[320733749248,420733749248],"startOrganId":100280}
        JSONObject jsonObject = JSONUtil.parseObj(msg);
        // 获取到司机id列表
        JSONArray driverIds = jsonObject.getJSONArray("driverIds");
        // 分配状态
        TransportTaskAssignedStatus assignedStatus = CollUtil.isEmpty(driverIds) ?
                TransportTaskAssignedStatus.MANUAL_DISTRIBUTED : TransportTaskAssignedStatus.DISTRIBUTED;
        // 创建运输任务
        Long transportTaskId = this.createTransportTask(jsonObject, assignedStatus);

        if (CollUtil.isEmpty(driverIds)) {
            log.info("生成司机作业单，司机列表为空，需要手动设置司机作业单 -> msg = {}", msg);
            return;
        }
        for (Object driverId : driverIds) {
            // 生成司机作业单
            this.driverJobFeign.createDriverJob(transportTaskId, Convert.toLong(driverId));
        }
    }

    @Transactional
    public Long createTransportTask(JSONObject jsonObject, TransportTaskAssignedStatus assignedStatus) {
        // 根据车辆计划id查询预计发车时间和预计到达时间
        Long truckPlanId = jsonObject.getLong("truckPlanId");
        TruckPlanDto truckPlanDto = truckPlanFeign.findById(truckPlanId);

        // 创建运输任务
        TransportTaskEntity transportTaskEntity = new TransportTaskEntity();
        transportTaskEntity.setTruckPlanId(jsonObject.getLong("truckPlanId"));
        transportTaskEntity.setTruckId(jsonObject.getLong("truckId"));
        transportTaskEntity.setStartAgencyId(jsonObject.getLong("startOrganId"));
        transportTaskEntity.setEndAgencyId(jsonObject.getLong("endOrganId"));
        transportTaskEntity.setTransportTripsId(jsonObject.getLong("transportTripsId"));
        // 任务分配状态
        transportTaskEntity.setAssignedStatus(assignedStatus);
        // 计划发车时间
        transportTaskEntity.setPlanDepartureTime(truckPlanDto.getPlanDepartureTime());
        // 计划到达时间
        transportTaskEntity.setPlanArrivalTime(truckPlanDto.getPlanArrivalTime());
        // 设置运输任务状态
        transportTaskEntity.setStatus(TransportTaskStatus.PENDING);

        // TODO 完善满载状态
        if (CollUtil.isEmpty(jsonObject.getJSONArray("transportOrderIdList"))) {
            transportTaskEntity.setLoadingStatus(TransportTaskLoadingStatus.EMPTY);
        } else {
            transportTaskEntity.setLoadingStatus(TransportTaskLoadingStatus.FULL);
        }

        // 查询路线距离
        TransportLineSearchDTO transportLineSearchDTO = new TransportLineSearchDTO();
        transportLineSearchDTO.setPage(1);
        transportLineSearchDTO.setPageSize(1);
        transportLineSearchDTO.setStartOrganId(transportTaskEntity.getStartAgencyId());
        transportLineSearchDTO.setEndOrganId(transportTaskEntity.getEndAgencyId());
        PageResponse<TransportLineDTO> transportLineResponse =
                this.transportLineFeign.queryPageList(transportLineSearchDTO);
        TransportLineDTO transportLineDTO = CollUtil.getFirst(transportLineResponse.getItems());
        if (ObjectUtil.isNotEmpty(transportLineDTO)) {
            // 设置距离
            transportTaskEntity.setDistance(transportLineDTO.getDistance());
        }

        // 保存数据
        this.transportTaskService.save(transportTaskEntity);

        // 创建运输任务与运单之间的关系
        this.createTransportOrderTask(transportTaskEntity.getId(), jsonObject);
        return transportTaskEntity.getId();
    }

    private void createTransportOrderTask(final Long transportTaskId, final JSONObject jsonObject) {
        // 创建运输任务与运单之间的关系
        JSONArray transportOrderIdList = jsonObject.getJSONArray("transportOrderIdList");
        if (CollUtil.isEmpty(transportOrderIdList)) {
            return;
        }

        // 将运单id列表转成运单实体列表
        List<TransportOrderTaskEntity> resultList = transportOrderIdList.stream()
                .map(o -> {
                    TransportOrderTaskEntity transportOrderTaskEntity = new TransportOrderTaskEntity();
                    transportOrderTaskEntity.setTransportTaskId(transportTaskId);
                    transportOrderTaskEntity.setTransportOrderId(Convert.toStr(o));
                    return transportOrderTaskEntity;
                }).collect(Collectors.toList());

        // 批量保存运输任务与运单的关联表
        this.transportOrderTaskService.batchSaveTransportOrder(resultList);

        // 批量标记运单为已调度状态
        List<TransportOrderEntity> list = transportOrderIdList.stream()
                .map(o -> {
                    TransportOrderEntity transportOrderEntity = new TransportOrderEntity();
                    transportOrderEntity.setId(Convert.toStr(o));
                    // 状态设置为已调度
                    transportOrderEntity.setSchedulingStatus(TransportOrderSchedulingStatus.SCHEDULED);
                    return transportOrderEntity;
                }).collect(Collectors.toList());
        this.transportOrderService.updateBatchById(list);
    }
}
