package com.sigma429.sl.service;


import com.sigma429.sl.dto.CourierTaskDTO;
import com.sigma429.sl.dto.CourierTaskPageQueryDTO;
import com.sigma429.sl.entity.CourierTaskEntity;
import com.sigma429.sl.util.PageResponse;

import java.util.List;

/**
 * 快递员任务服务接口
 **/
public interface CourierTaskService {
    /**
     * 分页查询
     * @param pageQueryDTO 分页查询条件
     * @return 分页查询结果
     */
    PageResponse<CourierTaskDTO> pageQuery(CourierTaskPageQueryDTO pageQueryDTO);

    /**
     * 新增快递员任务
     * @param courierTaskDTO 快递员任务
     */
    void saveOrUpdate(CourierTaskDTO courierTaskDTO);

    /**
     * 根据取派件id查询快递员任务
     * @param id 取派件id
     * @return 快递员任务
     */
    CourierTaskDTO findById(Long id);

    /**
     * 根据订单id查询快递员任务
     * @param orderId 订单id
     * @return 快递员任务列表
     */
    List<CourierTaskEntity> findByOrderId(Long orderId);
}
