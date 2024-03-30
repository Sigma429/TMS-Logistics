package com.sigma429.sl.service.impl;

import com.sigma429.sl.dto.OrderDTO;
import com.sigma429.sl.service.OmsService;
import com.sigma429.sl.util.PageResponse;
import com.sigma429.sl.vo.oms.*;
import com.sigma429.sl.vo.oms.pay.TradeLaunchVO;
import com.sigma429.sl.vo.oms.pay.TradeResponseVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * ClassName:OmsServiceImpl
 * Package:com.sigma429.sl.service.impl
 * Description:
 * @Author:14亿少女的梦-Sigma429
 * @Create:2024/03/06 - 13:33
 * @Version:v1.0
 */
@Service
public class OmsServiceImpl implements OmsService {
    @Override
    public OrderVO findOrderSimple(Long id) {
        return null;
    }

    @Override
    public OrderVO findOrderDetail(Long id) {
        return null;
    }

    @Override
    public void updateOrder(Long id, OrderVO vo) {

    }

    @Override
    public void delOrder(Long id) {

    }

    @Override
    public PageResponse<OrderVO> findByPage(OrderQueryVO vo) {
        return null;
    }

    @Override
    public List<OrderCargoVO> findAll(Long tranOrderId, Long orderId) {
        return null;
    }

    @Override
    public void save(OrderCargoUpdateVO vo) {

    }

    @Override
    public void update(Long id, OrderCargoUpdateVO vo) {

    }

    @Override
    public void del(Long id) {

    }

    @Override
    public List<OrderCargoVO> hotGood(String name) {
        return null;
    }

    @Override
    public Map<Integer, Long> count() {
        return null;
    }

    @Override
    public OrderVO mailingSave(MailingSaveVO vo) {
        return null;
    }

    @Override
    public TradeResponseVO pay(TradeLaunchVO tradeLaunchVO) {
        return null;
    }

    @Override
    public void cancel(Long id) {

    }

    @Override
    public OrderCarriageVO totalPrice(MailingSaveVO mailingSaveVO) {
        return null;
    }

    @Override
    public List<OrderCargoVO> lastGood(String name) {
        return null;
    }

    @Override
    public List<OrderVO> batchParseOrderDTO2Vo(List<OrderDTO> orderDTOList) {
        return null;
    }

    @Override
    public TrackVO findTrackById(String id) {
        return null;
    }
}
