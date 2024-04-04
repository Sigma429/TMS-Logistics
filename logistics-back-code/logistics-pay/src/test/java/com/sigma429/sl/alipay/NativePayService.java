package com.sigma429.sl.alipay;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.IdUtil;
import com.sigma429.sl.SimpleRedisLock;
import com.sigma429.sl.entity.TradingEntity;
import com.sigma429.sl.handler.NativePayHandler;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:NativePayService
 * Package:com.sigma429.sl.alipay
 * Description:
 * @Author:14亿少女的梦-Sigma429
 * @Create:2024/03/24 - 15:35
 * @Version:v1.0
 */
@Service
public class NativePayService {

    @Resource(name = "aliNativePayHandler")
    private NativePayHandler nativePayHandler;
    @Resource
    private SimpleRedisLock simpleRedisLock;

    @Resource
    private RedissonClient redissonClient;

    /**
     * 创建交易单示例代码
     * @param productOrderNo 订单号
     * @return 交易单对象
     */
    public TradingEntity createDownLineTrading(Long productOrderNo) {
        TradingEntity tradingEntity = new TradingEntity();
        tradingEntity.setProductOrderNo(productOrderNo);

        // 基于订单创建交易单
        tradingEntity.setTradingOrderNo(IdUtil.getSnowflakeNextId());
        tradingEntity.setCreated(LocalDateTime.now());
        tradingEntity.setTradingAmount(BigDecimal.valueOf(1));
        tradingEntity.setMemo("运费");

        // 调用三方支付创建交易
        this.nativePayHandler.createDownLineTrading(tradingEntity);

        return tradingEntity;
    }

    /**
     * 创建交易单示例代码
     * @param productOrderNo 订单号
     * @return 交易单对象
     */
    public TradingEntity createDownLineTradingLock(Long productOrderNo) {

        // 获取锁
        String lockName = Convert.toStr(productOrderNo);
        boolean lock = simpleRedisLock.tryLock(lockName, 5L);
        if (!lock) {
            System.out.println("没有获取到锁，线程id = " + Thread.currentThread().getId());
            return null;
        }

        System.out.println("获取到锁，线程id = " + Thread.currentThread().getId());

        TradingEntity tradingEntity = createDownLineTrading(productOrderNo);

        // 释放锁
        simpleRedisLock.unlock(lockName);
        return tradingEntity;
    }

    public TradingEntity createDownLineTradingRedissonLock(Long productOrderNo) {
        // 获取锁
        String lockName = Convert.toStr(productOrderNo);
        // 获取公平锁,优先分配给先发出请求的线程
        RLock lock = redissonClient.getFairLock(lockName);
        try {
            // 尝试获取锁，最长等待获取锁的时间为5秒
            if (lock.tryLock(5L, TimeUnit.SECONDS)) {
                System.out.println("获取到锁，线程id = " + Thread.currentThread().getId());
                // 休眠5s目的是让线程执行慢一些，容易测试出并发效果
                Thread.sleep(5000);
                return createDownLineTrading(productOrderNo);
            }
            System.out.println("没有获取到锁，线程id = " + Thread.currentThread().getId());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放锁，需要判断当前线程是否获取到锁
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        return null;
    }

}