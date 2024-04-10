package com.sigma429.sl.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.sigma429.sl.PickupDispatchTaskFeign;
import com.sigma429.sl.TrackFeign;
import com.sigma429.sl.TransportOrderFeign;
import com.sigma429.sl.dto.PickupDispatchTaskDTO;
import com.sigma429.sl.dto.TransportOrderDTO;
import com.sigma429.sl.dto.request.PickupDispatchTaskPageQueryDTO;
import com.sigma429.sl.enums.pickupDispatchtask.PickupDispatchTaskStatus;
import com.sigma429.sl.enums.pickupDispatchtask.PickupDispatchTaskType;
import com.sigma429.sl.service.TrackService;
import com.sigma429.sl.util.PageResponse;
import com.sigma429.sl.util.UserThreadLocal;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrackServiceImpl implements TrackService {
    @Resource
    private TrackFeign trackFeign;
    @Resource
    private PickupDispatchTaskFeign pickupDispatchTaskFeign;
    @Resource
    private TransportOrderFeign transportOrderFeign;

    /**
     * 快递员上报位置
     * @param lng 经度
     * @param lat 纬度
     */
    @Override
    public Boolean uploadLocation(String lng, String lat) {
        // 1. 获取当前用户id
        Long userId = UserThreadLocal.getUserId();

        // 2. 根据快递员id查询关联的新建状态派件任务
        PickupDispatchTaskPageQueryDTO pageQueryDTO = PickupDispatchTaskPageQueryDTO.builder()
                .page(1)
                .pageSize(999)
                .courierId(userId)
                // 新建取件任务时，还未生成运单，所以只查询新建的派件任务。也只能上报派件任务的快递员位置
                .taskType(PickupDispatchTaskType.DISPATCH)
                .status(PickupDispatchTaskStatus.NEW)
                .build();
        PageResponse<PickupDispatchTaskDTO> pageResponse = pickupDispatchTaskFeign.findByPage(pageQueryDTO);
        if (CollUtil.isEmpty(pageResponse.getItems())) {
            return true;
        }

        // 3. 从取派件任务中提取订单号
        Long[] orderIds =
                pageResponse.getItems().parallelStream().map(PickupDispatchTaskDTO::getOrderId).toArray(Long[]::new);

        // 4. 根据订单id查询运单
        List<TransportOrderDTO> transportOrderDTOS = transportOrderFeign.findByOrderIds(orderIds);

        // 5. 从运单中提取运单号
        List<String> transportOrderIds =
                transportOrderDTOS.parallelStream().map(TransportOrderDTO::getId).collect(Collectors.toList());

        // 6. 上报位置
        return trackFeign.uploadFromCourier(transportOrderIds, Double.parseDouble(lng), Double.parseDouble(lat));
    }
}
