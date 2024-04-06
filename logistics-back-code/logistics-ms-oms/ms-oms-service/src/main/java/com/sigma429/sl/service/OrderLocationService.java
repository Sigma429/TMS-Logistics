package com.sigma429.sl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sigma429.sl.dto.OrderLocationDTO;
import com.sigma429.sl.entity.OrderLocationEntity;

/**
 * 位置信息 服务
 */
public interface OrderLocationService extends IService<OrderLocationEntity> {

    OrderLocationDTO findOrderLocationByOrderId(Long orderId);
}
