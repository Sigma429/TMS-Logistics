package com.sigma429.sl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.sigma429.sl.NativePayFeign;
import com.sigma429.sl.OrderFeign;
import com.sigma429.sl.TradingFeign;
import com.sigma429.sl.domain.TradingDTO;
import com.sigma429.sl.domain.request.NativePayDTO;
import com.sigma429.sl.domain.response.NativePayResponseDTO;
import com.sigma429.sl.dto.OrderDTO;
import com.sigma429.sl.enums.OrderPaymentStatus;
import com.sigma429.sl.enums.PayChannelEnum;
import com.sigma429.sl.enums.TradingStateEnum;
import com.sigma429.sl.exception.SLWebException;
import com.sigma429.sl.service.PayService;
import com.sigma429.sl.vo.pay.TradeLaunchVO;
import com.sigma429.sl.vo.pay.TradeResponseVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PayServiceImpl implements PayService {
    @Resource
    private NativePayFeign nativePayFeign;
    @Resource
    private TradingFeign tradingFeign;
    @Resource
    private OrderFeign orderFeign;
    @Value("${sl.wechat.enterpriseId}")
    private Long wechatEnterpriseId;
    @Value("${sl.ali.enterpriseId}")
    private Long aliEnterpriseId;

    /**
     * 获取支付qrcode
     * @param tradeLaunchVO 支付发起对象
     * @return 支付二维码相关数据
     */
    @Override
    public TradeResponseVO getQrCode(TradeLaunchVO tradeLaunchVO) {
        return null;
    }

    /**
     * 获取支付状态
     * @param productOrderNo 订单号
     * @return 是否支付成功
     */
    @Override
    public boolean getStatus(String productOrderNo) {
        return false;
    }
}
