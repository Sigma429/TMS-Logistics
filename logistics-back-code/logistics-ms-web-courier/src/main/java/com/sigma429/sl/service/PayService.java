package com.sigma429.sl.service;


import com.sigma429.sl.vo.pay.TradeLaunchVO;
import com.sigma429.sl.vo.pay.TradeResponseVO;

public interface PayService {
    /**
     * 获取支付qrcode
     *
     * @param tradeLaunchVO 支付发起对象
     * @return 支付二维码相关数据
     */
    TradeResponseVO getQrCode(TradeLaunchVO tradeLaunchVO);

    /**
     * 获取支付状态
     *
     * @param productOrderNo 订单号
     * @return 是否支付成功
     */
    boolean getStatus(String productOrderNo);
}
