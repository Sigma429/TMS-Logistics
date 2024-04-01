package com.sigma429.sl.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sigma429.sl.OrderFeign;
import com.sigma429.sl.TransportLineFeign;
import com.sigma429.sl.common.MQFeign;
import com.sigma429.sl.constant.Constants;
import com.sigma429.sl.domain.TransportLineNodeDTO;
import com.sigma429.sl.dto.OrderCargoDTO;
import com.sigma429.sl.dto.OrderDetailDTO;
import com.sigma429.sl.dto.OrderLocationDTO;
import com.sigma429.sl.dto.TransportOrderDTO;
import com.sigma429.sl.dto.request.TransportOrderQueryDTO;
import com.sigma429.sl.dto.response.TransportOrderStatusCountDTO;
import com.sigma429.sl.entity.TransportOrderEntity;
import com.sigma429.sl.entity.TransportOrderTaskEntity;
import com.sigma429.sl.enums.IdEnum;
import com.sigma429.sl.enums.WorkExceptionEnum;
import com.sigma429.sl.enums.pickupDispatchtask.PickupDispatchTaskType;
import com.sigma429.sl.enums.transportorder.TransportOrderSchedulingStatus;
import com.sigma429.sl.enums.transportorder.TransportOrderStatus;
import com.sigma429.sl.exception.SLException;
import com.sigma429.sl.mapper.TransportOrderMapper;
import com.sigma429.sl.mapper.TransportOrderTaskMapper;
import com.sigma429.sl.service.IdService;
import com.sigma429.sl.service.TransportOrderService;
import com.sigma429.sl.util.ObjectUtil;
import com.sigma429.sl.util.PageResponse;
import com.sigma429.sl.vo.OrderMsg;
import com.sigma429.sl.vo.TransportOrderMsg;
import com.sigma429.sl.vo.TransportOrderStatusMsg;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ClassName:TransportOrderServiceImpl
 * Package:com.sigma429.sl.service.impl
 * Description:
 * @Author:14亿少女的梦-Sigma429
 * @Create:2024/04/01 - 15:55
 * @Version:v1.0
 */
public class TransportOrderServiceImpl extends
        ServiceImpl<TransportOrderMapper, TransportOrderEntity> implements TransportOrderService {
    @Resource
    private OrderFeign orderFeign;
    @Resource
    private TransportLineFeign transportLineFeign;
    @Resource
    private IdService idService;

    @Resource
    private MQFeign mqFeign;

    @Override
    @Transactional
    public TransportOrderEntity orderToTransportOrder(Long orderId) {
        // 幂等性校验
        TransportOrderEntity transportOrderEntity = this.findByOrderId(orderId);
        if (ObjectUtil.isNotEmpty(transportOrderEntity)) {
            return transportOrderEntity;
        }

        // 查询订单
        OrderDetailDTO detailByOrder = this.orderFeign.findDetailByOrderId(orderId);
        if (ObjectUtil.isEmpty(detailByOrder)) {
            throw new SLException(WorkExceptionEnum.ORDER_NOT_FOUND);
        }

        // 校验货物的重量和体积数据
        OrderCargoDTO cargoDto = detailByOrder.getOrderDTO().getOrderCargoDto();
        if (ObjectUtil.isEmpty(cargoDto)) {
            throw new SLException(WorkExceptionEnum.ORDER_CARGO_NOT_FOUND);
        }

        // 校验位置信息
        OrderLocationDTO orderLocationDTO = detailByOrder.getOrderLocationDTO();
        if (ObjectUtil.isEmpty(orderLocationDTO)) {
            throw new SLException(WorkExceptionEnum.ORDER_LOCATION_NOT_FOUND);
        }

        // 起始网点id
        Long sendAgentId = Convert.toLong(orderLocationDTO.getSendAgentId());
        // 终点网点id
        Long receiveAgentId = Convert.toLong(orderLocationDTO.getReceiveAgentId());

        // 默认参与调度
        boolean isDispatch = true;
        TransportLineNodeDTO transportLineNodeDTO = null;
        if (ObjectUtil.equal(sendAgentId, receiveAgentId)) {
            // 起点、终点是同一个网点，不需要规划路线，直接发消息生成派件任务即可
            isDispatch = false;
        } else {
            // 根据起始机构规划运输路线
            transportLineNodeDTO = this.transportLineFeign.queryPathByDispatchMethod(sendAgentId, receiveAgentId);
            if (ObjectUtil.isEmpty(transportLineNodeDTO) || CollUtil.isEmpty(transportLineNodeDTO.getNodeList())) {
                throw new SLException(WorkExceptionEnum.TRANSPORT_LINE_NOT_FOUND);
            }
        }

        // 创建新的运单对象
        TransportOrderEntity transportOrder = new TransportOrderEntity();

        // 设置id
        transportOrder.setId(this.idService.getId(IdEnum.TRANSPORT_ORDER));
        // 订单ID
        transportOrder.setOrderId(orderId);
        // 起始网点id
        transportOrder.setStartAgencyId(sendAgentId);
        // 终点网点id
        transportOrder.setEndAgencyId(receiveAgentId);
        // 当前所在机构id
        transportOrder.setCurrentAgencyId(sendAgentId);

        if (ObjectUtil.isNotEmpty(transportLineNodeDTO)) {
            // 运单状态(1.新建 2.已装车 3.运输中 4.到达终端网点 5.已签收 6.拒收)
            transportOrder.setStatus(TransportOrderStatus.CREATED);
            // 调度状态(1.待调度2.未匹配线路3.已调度)
            transportOrder.setSchedulingStatus(TransportOrderSchedulingStatus.TO_BE_SCHEDULED);
            // 下一个机构id
            transportOrder.setNextAgencyId(transportLineNodeDTO.getNodeList().get(1).getId());
            // 完整的运输路线
            transportOrder.setTransportLine(JSONUtil.toJsonStr(transportLineNodeDTO));
        } else {
            // 下个网点就是当前网点
            transportOrder.setNextAgencyId(sendAgentId);
            // 运单状态(1.新建 2.已装车 3.运输中 4.到达终端网点 5.已签收 6.拒收)
            transportOrder.setStatus(TransportOrderStatus.ARRIVED_END);
            // 调度状态(1.待调度2.未匹配线路3.已调度)
            transportOrder.setSchedulingStatus(TransportOrderSchedulingStatus.SCHEDULED);
        }

        // 货品总体积，单位m^3
        transportOrder.setTotalVolume(cargoDto.getVolume());
        // 货品总重量，单位kg
        transportOrder.setTotalWeight(cargoDto.getWeight());
        // 默认非拒收订单
        transportOrder.setIsRejection(false);

        boolean result = super.save(transportOrder);
        if (result) {
            if (isDispatch) {
                // 发送消息到调度中心，进行调度
                this.sendTransportOrderMsgToDispatch(transportOrder);
            } else {
                // 不需要调度 发送消息更新订单状态
                this.sendUpdateStatusMsg(ListUtil.toList(transportOrder.getId()), TransportOrderStatus.ARRIVED_END);
                // 不需要调度，发送消息生成派件任务
                this.sendDispatchTaskMsgToDispatch(transportOrder);
            }

            // 发消息通知其他系统，运单已经生成
            String msg = TransportOrderMsg.builder()
                    .id(transportOrder.getId())
                    .orderId(transportOrder.getOrderId())
                    .created(DateUtil.current())
                    .build().toJson();
            this.mqFeign.sendMsg(Constants.MQ.Exchanges.TRANSPORT_ORDER_DELAYED,
                    Constants.MQ.RoutingKeys.TRANSPORT_ORDER_CREATE, msg, Constants.MQ.NORMAL_DELAY);

            return transportOrder;
        }
        // 保存失败
        throw new SLException(WorkExceptionEnum.TRANSPORT_ORDER_SAVE_ERROR);
    }

    /**
     * 发送运单消息到调度中，参与调度
     */
    private void sendTransportOrderMsgToDispatch(TransportOrderEntity transportOrder) {
        Map<Object, Object> msg = MapUtil.builder()
                .put("transportOrderId", transportOrder.getId())
                .put("currentAgencyId", transportOrder.getCurrentAgencyId())
                .put("nextAgencyId", transportOrder.getNextAgencyId())
                .put("totalWeight", transportOrder.getTotalWeight())
                .put("totalVolume", transportOrder.getTotalVolume())
                .put("created", System.currentTimeMillis()).build();
        String jsonMsg = JSONUtil.toJsonStr(msg);
        // 发送消息，延迟5秒，确保本地事务已经提交，可以查询到数据
        this.mqFeign.sendMsg(Constants.MQ.Exchanges.TRANSPORT_ORDER_DELAYED,
                Constants.MQ.RoutingKeys.JOIN_DISPATCH, jsonMsg, Constants.MQ.LOW_DELAY);
    }

    /**
     * 发送生成取派件任务的消息
     * @param transportOrder 运单对象
     */
    private void sendDispatchTaskMsgToDispatch(TransportOrderEntity transportOrder) {
        // 预计完成时间，如果是中午12点到的快递，当天22点前，否则，第二天22点前
        int offset = 0;
        if (LocalDateTime.now().getHour() >= 12) {
            offset = 1;
        }
        LocalDateTime estimatedEndTime = DateUtil.offsetDay(new Date(), offset)
                .setField(DateField.HOUR_OF_DAY, 22)
                .setField(DateField.MINUTE, 0)
                .setField(DateField.SECOND, 0)
                .setField(DateField.MILLISECOND, 0).toLocalDateTime();

        // 发送分配快递员派件任务的消息
        OrderMsg orderMsg = OrderMsg.builder()
                .agencyId(transportOrder.getCurrentAgencyId())
                .orderId(transportOrder.getOrderId())
                .created(DateUtil.current())
                // 派件任务
                .taskType(PickupDispatchTaskType.DISPATCH.getCode())
                .mark("系统提示：派件前请与收件人电话联系.")
                .estimatedEndTime(estimatedEndTime).build();

        // 发送消息
        this.sendPickupDispatchTaskMsgToDispatch(transportOrder, orderMsg);
    }

    /**
     * 发送消息到调度中心，用于生成取派件任务
     * @param transportOrder 运单
     * @param orderMsg       消息内容
     */
    @Override
    public void sendPickupDispatchTaskMsgToDispatch(TransportOrderEntity transportOrder, OrderMsg orderMsg) {
        // 查询订单对应的位置信息
        OrderLocationDTO orderLocationDTO = this.orderFeign.findOrderLocationByOrderId(orderMsg.getOrderId());

        // (1)运单为空：取件任务取消，取消原因为返回网点；重新调度位置取寄件人位置
        // (2)运单不为空：生成的是派件任务，需要根据拒收状态判断位置是寄件人还是收件人
        // 拒收：寄件人  其他：收件人
        String location;
        if (ObjectUtil.isEmpty(transportOrder)) {
            location = orderLocationDTO.getSendLocation();
        } else {
            location = transportOrder.getIsRejection() ? orderLocationDTO.getSendLocation() :
                    orderLocationDTO.getReceiveLocation();
        }

        Double[] coordinate = Convert.convert(Double[].class, StrUtil.split(location, ","));
        Double longitude = coordinate[0];
        Double latitude = coordinate[1];

        // 设置消息中的位置信息
        orderMsg.setLongitude(longitude);
        orderMsg.setLatitude(latitude);

        // 发送消息,用于生成取派件任务
        this.mqFeign.sendMsg(Constants.MQ.Exchanges.ORDER_DELAYED, Constants.MQ.RoutingKeys.ORDER_CREATE,
                orderMsg.toJson(), Constants.MQ.NORMAL_DELAY);
    }

    private void sendUpdateStatusMsg(List<String> ids, TransportOrderStatus transportOrderStatus) {
        String msg = TransportOrderStatusMsg.builder()
                .idList(ids)
                .statusName(transportOrderStatus.name())
                .statusCode(transportOrderStatus.getCode())
                .build().toJson();

        // 将状态名称写入到路由key中，方便消费方选择性的接收消息
        String routingKey = Constants.MQ.RoutingKeys.TRANSPORT_ORDER_UPDATE_STATUS_PREFIX + transportOrderStatus.name();
        this.mqFeign.sendMsg(Constants.MQ.Exchanges.TRANSPORT_ORDER_DELAYED, routingKey, msg, Constants.MQ.LOW_DELAY);
    }

    @Override
    public Page<TransportOrderEntity> findByPage(TransportOrderQueryDTO transportOrderQueryDTO) {
        return null;
    }

    @Override
    public TransportOrderEntity findByOrderId(Long orderId) {
        return null;
    }

    @Override
    public List<TransportOrderEntity> findByOrderIds(Long[] orderIds) {
        return null;
    }

    @Override
    public List<TransportOrderEntity> findByIds(String[] ids) {
        return null;
    }

    @Override
    public List<TransportOrderEntity> searchById(String id) {
        return null;
    }

    @Override
    public boolean updateStatus(List<String> ids, TransportOrderStatus transportOrderStatus) {
        return false;
    }

    @Override
    public boolean updateByTaskId(Long taskId) {
        return false;
    }

    @Override
    public List<TransportOrderStatusCountDTO> findStatusCount() {
        return null;
    }


    @Override
    public PageResponse<TransportOrderDTO> pageQueryByTaskId(Integer page, Integer pageSize, String taskId,
                                                             String transportOrderId) {
        return null;
    }
}
