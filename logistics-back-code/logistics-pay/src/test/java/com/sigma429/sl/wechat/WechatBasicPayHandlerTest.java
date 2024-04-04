package com.sigma429.sl.wechat;

import com.sigma429.sl.entity.RefundRecordEntity;
import com.sigma429.sl.entity.TradingEntity;
import com.sigma429.sl.handler.BasicPayHandler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootTest
class WechatBasicPayHandlerTest {

    @Resource(name = "weChatBasicPayHandler")
    BasicPayHandler basicPayHandler;

    @Test
    void queryTrading() {
        TradingEntity tradingEntity = new TradingEntity();
        tradingEntity.setTradingOrderNo(11228763388L); // 交易单号
        tradingEntity.setCreated(LocalDateTime.now());
        Boolean result = this.basicPayHandler.queryTrading(tradingEntity);
        System.out.println("执行是否成功：" + result);
        System.out.println(tradingEntity);
    }

    @Test
    void closeTrading() {
        TradingEntity tradingEntity = new TradingEntity();
        tradingEntity.setTradingOrderNo(11223377L); // 交易单号
        Boolean result = this.basicPayHandler.closeTrading(tradingEntity);
        System.out.println("执行是否成功：" + result);
        System.out.println(tradingEntity);
    }

    @Test
    void refundTrading() {
        RefundRecordEntity refundRecordEntity = new RefundRecordEntity();
        refundRecordEntity.setTradingOrderNo(11223388L); // 交易单号
        refundRecordEntity.setRefundNo(11223380L); // 退款单号
        refundRecordEntity.setRefundAmount(BigDecimal.valueOf(0.1)); // 退款金额
        Boolean result = this.basicPayHandler.refundTrading(refundRecordEntity);
        System.out.println("执行是否成功：" + result);
        System.out.println(refundRecordEntity);
    }

    @Test
    void queryRefundTrading() {
        RefundRecordEntity refundRecordEntity = new RefundRecordEntity();
        refundRecordEntity.setTradingOrderNo(11223388L); // 交易单号
        refundRecordEntity.setRefundNo(11223388L); // 退款单号
        Boolean result = this.basicPayHandler.queryRefundTrading(refundRecordEntity);
        System.out.println("执行是否成功：" + result);
        System.out.println(refundRecordEntity);
    }
}