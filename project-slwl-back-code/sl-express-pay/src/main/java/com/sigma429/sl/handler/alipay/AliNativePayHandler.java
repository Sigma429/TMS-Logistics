package com.sigma429.sl.handler.alipay;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONUtil;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import com.sigma429.sl.entity.TradingEntity;
import com.sigma429.sl.enums.TradingStateEnum;
import com.sigma429.sl.exception.SLException;
import com.sigma429.sl.handler.NativePayHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 支付宝实现类
 */
@Component("aliNativePayHandler")
@Slf4j
public class AliNativePayHandler implements NativePayHandler {

    @Override
    public void createDownLineTrading(TradingEntity tradingEntity) throws SLException {
        // 1. 设置参数（全局只需设置一次）
        Factory.setOptions(AlipayConfig.getConfig());
        try {
            // 2. 发起API调用（以创建当面付收款二维码为例）
            AlipayTradePrecreateResponse response = Factory.Payment.FaceToFace()
                    // 订单描述
                    .preCreate(tradingEntity.getMemo(),
                            // 交易单号
                            Convert.toStr(tradingEntity.getTradingOrderNo()),
                            // 交易金额
                            Convert.toStr(tradingEntity.getTradingAmount()));
            // 3. 处理响应或异常
            if (ResponseChecker.success(response)) {
                log.info("调用成功");
                // 二维码信息
                tradingEntity.setPlaceOrderMsg(response.getQrCode());
                tradingEntity.setPlaceOrderCode(response.getCode());
                tradingEntity.setPlaceOrderJson(JSONUtil.toJsonStr(response));
                tradingEntity.setTradingState(TradingStateEnum.FKZ);
            } else {
                log.error("调用失败，原因：" + response.msg + "，" + response.subMsg);
            }
        } catch (Exception e) {
            log.error("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }


}
