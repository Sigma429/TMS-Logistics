package com.sigma429.sl.job;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class TradeJobTest {

    @Resource
    private TradeJob tradeJob;

    @Test
    public void tradingJob() {
        this.tradeJob.tradingJob();
    }

    @Test
    public void refundJob() {
        this.tradeJob.refundJob();
    }
}