package com.sigma429.sl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sigma429.sl.dto.MailingSaveDTO;
import com.sigma429.sl.dto.OrderCarriageDTO;
import com.sigma429.sl.dto.OrderDTO;
import com.sigma429.sl.entity.OrderEntity;

/**
 * 订单状态  服务类
 */
public interface OrderService extends IService<OrderEntity> {

    /**
     * 下单
     * @param mailingSaveDTO 下单信息
     * @return 下单成功信息
     * @throws Exception 异常
     */
    OrderDTO mailingSave(MailingSaveDTO mailingSaveDTO) throws Exception;

    /**
     * 预估总价
     * @param mailingSaveDTO 下单信息
     * @return 运费预估信息
     */
    OrderCarriageDTO totalPrice(MailingSaveDTO mailingSaveDTO);
}
