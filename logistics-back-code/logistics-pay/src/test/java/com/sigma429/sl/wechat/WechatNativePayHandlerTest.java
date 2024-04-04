package com.sigma429.sl.wechat;

import com.sigma429.sl.entity.TradingEntity;
import com.sigma429.sl.handler.NativePayHandler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.math.BigDecimal;

@SpringBootTest
@Controller
class WechatNativePayHandlerTest {

    @Resource(name = "wechatNativePayHandler")
    NativePayHandler nativePayHandler;

    @Test
    void createDownLineTrading() {
        TradingEntity tradingEntity = new TradingEntity();
        tradingEntity.setProductOrderNo(1234765L); //订单号
        tradingEntity.setTradingOrderNo(11228763388L); //交易单号
        tradingEntity.setMemo("运费");
        tradingEntity.setTradingAmount(BigDecimal.valueOf(1));
        this.nativePayHandler.createDownLineTrading(tradingEntity);

        System.out.println("二维码信息：" + tradingEntity.getPlaceOrderMsg());
        System.out.println(tradingEntity);
    }
}