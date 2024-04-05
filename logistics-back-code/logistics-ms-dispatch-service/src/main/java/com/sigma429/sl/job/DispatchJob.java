package com.sigma429.sl.job;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.sigma429.sl.common.MQFeign;
import com.sigma429.sl.constant.Constants;
import com.sigma429.sl.dto.DispatchMsgDTO;
import com.sigma429.sl.mq.TransportOrderDispatchMQListener;
import com.sigma429.sl.truck.TruckDto;
import com.sigma429.sl.truck.TruckPlanDto;
import com.sigma429.sl.truck.TruckPlanFeign;
import com.sigma429.sl.util.ObjectUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 调度运输任务
 */
@Component
@Slf4j
public class DispatchJob {
    @Resource
    private TransportOrderDispatchMQListener transportOrderDispatchMQListener;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private TruckPlanFeign truckPlanFeign;
    @Resource
    private MQFeign mqFeign;

    @Value("${sl.volume.ratio:0.95}")
    private Double volumeRatio;
    @Value("${sl.weight.ratio:0.95}")
    private Double weightRatio;


    /**
     * 分片广播方式处理运单，生成运输任务，可并行多处理车辆，提升调度处理效率
     */
    @XxlJob("transportTask")
    public void transportTask() {
        // 分片参数
        int shardIndex = XxlJobHelper.getShardIndex();
        int shardTotal = XxlJobHelper.getShardTotal();

        // 根据分片参数 2小时内并且可用状态车辆
        // List<TruckPlanDto> truckDtoList = this.queryTruckPlanDtoList(shardIndex, shardTotal);
        List<TruckPlanDto> truckDtoList = this.truckPlanFeign.pullUnassignedPlan(shardTotal, shardIndex);
        if (CollUtil.isEmpty(truckDtoList)) {
            return;
        }

        // 对每一个车辆都进行处理
        // 为了相同目的地的运单尽可能的分配在一个运输任务中，所以需要在读取数据时进行锁定，一个车辆处理完成后再开始下一个车辆处理
        // 在这里，使用redis的分布式锁实现
        for (TruckPlanDto truckPlanDto : truckDtoList) {
            // 校验车辆计划对象
            if (ObjectUtil.hasEmpty(truckPlanDto.getStartOrganId(), truckPlanDto.getEndOrganId(),
                    truckPlanDto.getTransportTripsId(), truckPlanDto.getId())) {
                log.error("车辆计划对象数据不符合要求， truckPlanDto -> {}", truckPlanDto);
                continue;
            }
            // 根据该车辆的开始、结束机构id，来确定要处理的运单数据集合
            Long startOrganId = truckPlanDto.getStartOrganId();
            Long endOrganId = truckPlanDto.getEndOrganId();
            String redisKey = this.transportOrderDispatchMQListener.getListRedisKey(startOrganId, endOrganId);
            String lockRedisKey = Constants.LOCKS.DISPATCH_LOCK_PREFIX + redisKey;
            // 获取锁
            RLock lock = this.redissonClient.getFairLock(lockRedisKey);
            List<DispatchMsgDTO> dispatchMsgDTOList = new ArrayList<>();
            try {
                // 锁定，一直等待锁，一定要获取到锁，因为查询到车辆的调度状态设置为：已分配
                lock.lock();
                // 计算车辆运力、合并运单
                this.executeTransportTask(redisKey, truckPlanDto.getTruckDto(), dispatchMsgDTOList);
            } finally {
                lock.unlock();
            }
            // 生成运输任务
            this.createTransportTask(truckPlanDto, startOrganId, endOrganId, dispatchMsgDTOList);
        }

        // 发送消息通知车辆已经完成调度
        this.completeTruckPlan(truckDtoList);
    }

    private void executeTransportTask(String redisKey, TruckDto truckDto, List<DispatchMsgDTO> dispatchMsgDTOList) {
        String redisData = this.stringRedisTemplate.opsForList().rightPop(redisKey);
        if (StrUtil.isEmpty(redisData)) {
            // 该车辆没有运单需要运输
            return;
        }
        DispatchMsgDTO dispatchMsgDTO = JSONUtil.toBean(redisData, DispatchMsgDTO.class);

        // 计算该车辆已经分配的运单，是否超出其运力，载重 或 体积超出，需要将新拿到的运单加进去后进行比较
        BigDecimal totalWeight = NumberUtil.add(NumberUtil.toBigDecimal(dispatchMsgDTOList.stream()
                .mapToDouble(DispatchMsgDTO::getTotalWeight)
                .sum()), dispatchMsgDTO.getTotalWeight());

        BigDecimal totalVolume = NumberUtil.add(NumberUtil.toBigDecimal(dispatchMsgDTOList.stream()
                .mapToDouble(DispatchMsgDTO::getTotalVolume)
                .sum()), dispatchMsgDTO.getTotalVolume());

        // 车辆最大的容积和载重要留有余量，否则可能会超重 或 装不下
        BigDecimal maxAllowableLoad = NumberUtil.mul(truckDto.getAllowableLoad(), weightRatio);
        BigDecimal maxAllowableVolume = NumberUtil.mul(truckDto.getAllowableVolume(), volumeRatio);

        if (NumberUtil.isGreaterOrEqual(totalWeight, maxAllowableLoad)
                || NumberUtil.isGreaterOrEqual(totalVolume, maxAllowableVolume)) {
            // 超出车辆运力，需要取货的运单再放回去，放到最右边，以便保证运单处理的顺序
            this.stringRedisTemplate.opsForList().rightPush(redisKey, redisData);
            return;
        }

        // 没有超出运力，将该运单加入到集合中
        dispatchMsgDTOList.add(dispatchMsgDTO);
        // 递归处理运单
        executeTransportTask(redisKey, truckDto, dispatchMsgDTOList);
    }

    private void createTransportTask(TruckPlanDto truckPlanDto, Long startOrganId, Long endOrganId,
                                     List<DispatchMsgDTO> dispatchMsgDTOList) {
        // 将运单合并的结果以消息的方式发送出去
        // key-> 车辆id，value ->  运单id列表
        //{"driverId":123, "truckPlanId":456, "truckId":1210114964812075008,"totalVolume":4.2,"endOrganId":90001,
        // "totalWeight":7,"transportOrderIdList":[320733749248,420733749248],"startOrganId":100280}
        List<String> transportOrderIdList = CollUtil.getFieldValues(dispatchMsgDTOList, "transportOrderId",
                String.class);
        // 司机列表确保不为null
        List<Long> driverIds = CollUtil.isNotEmpty(truckPlanDto.getDriverIds()) ? truckPlanDto.getDriverIds() :
                ListUtil.empty();
        Map<String, Object> msgResult = MapUtil.<String, Object>builder()
                // 车辆id
                .put("truckId", truckPlanDto.getTruckId())
                // 司机id
                .put("driverIds", driverIds)
                // 车辆计划id
                .put("truckPlanId", truckPlanDto.getId())
                // 车次id
                .put("transportTripsId", truckPlanDto.getTransportTripsId())
                // 开始机构id
                .put("startOrganId", startOrganId)
                // 结束机构id
                .put("endOrganId", endOrganId)
                // 运单id列表
                .put("transportOrderIdList", transportOrderIdList)
                // 总重量
                .put("totalWeight", dispatchMsgDTOList.stream()
                        .mapToDouble(DispatchMsgDTO::getTotalWeight)
                        .sum())
                // 总体积
                .put("totalVolume", dispatchMsgDTOList.stream()
                        .mapToDouble(DispatchMsgDTO::getTotalVolume)
                        .sum())
                .build();

        // 发送消息
        String jsonMsg = JSONUtil.toJsonStr(msgResult);
        this.mqFeign.sendMsg(Constants.MQ.Exchanges.TRANSPORT_TASK,
                Constants.MQ.RoutingKeys.TRANSPORT_TASK_CREATE, jsonMsg);

        if (CollUtil.isNotEmpty(transportOrderIdList)) {
            // 删除redis中set存储的运单数据
            String setRedisKey = this.transportOrderDispatchMQListener.getSetRedisKey(startOrganId, endOrganId);
            this.stringRedisTemplate.opsForSet().remove(setRedisKey, transportOrderIdList.toArray());
        }

    }

    private void completeTruckPlan(List<TruckPlanDto> truckDtoList) {
        //{"ids":[1,2,3], "created":123456}
        Map<String, Object> msg = MapUtil.<String, Object>builder()
                .put("ids", CollUtil.getFieldValues(truckDtoList, "id", Long.class))
                .put("created", System.currentTimeMillis()).build();
        String jsonMsg = JSONUtil.toJsonStr(msg);
        // 发送消息
        this.mqFeign.sendMsg(Constants.MQ.Exchanges.TRUCK_PLAN,
                Constants.MQ.RoutingKeys.TRUCK_PLAN_COMPLETE, jsonMsg);
    }
}
