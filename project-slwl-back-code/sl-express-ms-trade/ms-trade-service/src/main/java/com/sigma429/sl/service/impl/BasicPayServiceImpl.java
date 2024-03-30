package com.sigma429.sl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.NumberUtil;
import com.sigma429.sl.constant.Constant;
import com.sigma429.sl.constant.TradingCacheConstant;
import com.sigma429.sl.domain.RefundRecordDTO;
import com.sigma429.sl.domain.TradingDTO;
import com.sigma429.sl.entity.RefundRecordEntity;
import com.sigma429.sl.entity.TradingEntity;
import com.sigma429.sl.enums.TradingEnum;
import com.sigma429.sl.enums.TradingStateEnum;
import com.sigma429.sl.exception.SLException;
import com.sigma429.sl.handler.BasicPayHandler;
import com.sigma429.sl.handler.BeforePayHandler;
import com.sigma429.sl.handler.HandlerFactory;
import com.sigma429.sl.service.BasicPayService;
import com.sigma429.sl.service.RefundRecordService;
import com.sigma429.sl.service.TradingService;
import com.sigma429.sl.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * 支付的基础功能
 */
@Slf4j
@Service
public class BasicPayServiceImpl implements BasicPayService {

    @Resource
    private BeforePayHandler beforePayHandler;
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private TradingService tradingService;
    @Resource
    private RefundRecordService refundRecordService;

    @Override
    public TradingDTO queryTrading(Long tradingOrderNo) throws SLException {
        // 通过单号查询交易单数据
        TradingEntity trading = tradingService.findTradByTradingOrderNo(tradingOrderNo);
        // 查询前置处理：检测交易单参数
        beforePayHandler.checkQueryTrading(trading);

        String key = TradingCacheConstant.QUERY_PAY + tradingOrderNo;
        RLock lock = redissonClient.getFairLock(key);
        try {
            if (lock.tryLock(TradingCacheConstant.REDIS_WAIT_TIME, TimeUnit.SECONDS)) {
                // 选取不同的支付渠道实现
                BasicPayHandler handler = HandlerFactory.get(trading.getTradingChannel(), BasicPayHandler.class);
                Boolean result = handler.queryTrading(trading);
                if (result) {
                    // 如果交易单已经完成，需要将二维码数据删除，节省数据库空间，如果有需要可以再次生成
                    if (ObjectUtil.equalsAny(trading.getTradingState(), TradingStateEnum.YJS, TradingStateEnum.QXDD)) {
                        trading.setQrCode("");
                    }
                    // 更新数据
                    tradingService.saveOrUpdate(trading);
                }
                return BeanUtil.toBean(trading, TradingDTO.class);
            }
            throw new SLException(TradingEnum.NATIVE_QUERY_FAIL);
        } catch (SLException e) {
            throw e;
        } catch (Exception e) {
            log.error("查询交易单数据异常: trading = {}", trading, e);
            throw new SLException(TradingEnum.NATIVE_QUERY_FAIL);
        } finally {
            lock.unlock();
        }
    }

    @Override
    @Transactional
    public Boolean refundTrading(Long tradingOrderNo, BigDecimal refundAmount) throws SLException {
        // 通过单号查询交易单数据
        TradingEntity trading = tradingService.findTradByTradingOrderNo(tradingOrderNo);
        // 设置退款金额
        trading.setRefund(NumberUtil.add(refundAmount, trading.getRefund()));

        // 入库前置检查
        beforePayHandler.checkRefundTrading(trading);

        String key = TradingCacheConstant.REFUND_PAY + tradingOrderNo;
        RLock lock = redissonClient.getFairLock(key);
        try {
            // 获取锁
            if (lock.tryLock(TradingCacheConstant.REDIS_WAIT_TIME, TimeUnit.SECONDS)) {
                // 幂等性的检查
                RefundRecordEntity refundRecord = beforePayHandler.idempotentRefundTrading(trading, refundAmount);
                if (refundRecord == null) {
                    return false;
                }

                // 选取不同的支付渠道实现
                BasicPayHandler handler = HandlerFactory.get(refundRecord.getTradingChannel(), BasicPayHandler.class);
                Boolean result = handler.refundTrading(refundRecord);
                if (result) {
                    // 更新退款记录数据
                    refundRecordService.saveOrUpdate(refundRecord);

                    // 设置交易单是退款订单
                    trading.setIsRefund(Constant.YES);
                    tradingService.saveOrUpdate(trading);
                }
                return true;
            }
            throw new SLException(TradingEnum.NATIVE_QUERY_FAIL);
        } catch (SLException e) {
            throw e;
        } catch (Exception e) {
            log.error("查询交易单数据异常:{}", ExceptionUtil.stacktraceToString(e));
            throw new SLException(TradingEnum.NATIVE_QUERY_FAIL);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public RefundRecordDTO queryRefundTrading(Long refundNo) throws SLException {
        // 通过单号查询交易单数据
        RefundRecordEntity refundRecord = refundRecordService.findByRefundNo(refundNo);
        // 查询前置处理
        beforePayHandler.checkQueryRefundTrading(refundRecord);

        String key = TradingCacheConstant.REFUND_QUERY_PAY + refundNo;
        RLock lock = redissonClient.getFairLock(key);
        try {
            // 获取锁
            if (lock.tryLock(TradingCacheConstant.REDIS_WAIT_TIME, TimeUnit.SECONDS)) {

                // 选取不同的支付渠道实现
                BasicPayHandler handler = HandlerFactory.get(refundRecord.getTradingChannel(), BasicPayHandler.class);
                Boolean result = handler.queryRefundTrading(refundRecord);
                if (result) {
                    // 更新数据
                    refundRecordService.saveOrUpdate(refundRecord);
                }
                return BeanUtil.toBean(refundRecord, RefundRecordDTO.class);
            }
            throw new SLException(TradingEnum.REFUND_FAIL);
        } catch (SLException e) {
            throw e;
        } catch (Exception e) {
            log.error("查询退款交易单数据异常: refundRecord = {}", refundRecord, e);
            throw new SLException(TradingEnum.REFUND_FAIL);
        } finally {
            lock.unlock();
        }
    }
}
