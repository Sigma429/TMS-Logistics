package com.sigma429.sl.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 快递员消息
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CourierMsg extends BaseMsg {

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 快递员id
     */
    private Long courierId;

    /**
     * 快递员姓名
     */
    private String courierName;

    /**
     * 快递员手机号
     */
    private String courierMobile;

}
