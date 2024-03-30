package com.sigma429.sl.dto;

import lombok.Data;

@Data
public class OrderDetailDTO {

    /**
     * 订单
     */
    private OrderDTO orderDTO;

    /**
     * 货物
     */
    private OrderLocationDTO orderLocationDTO;
}
