package com.sigma429.sl.service.impl;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.sigma429.sl.constant.Constant;
import com.sigma429.sl.constant.TradingCacheConstant;
import com.sigma429.sl.constant.TradingConstant;
import com.sigma429.sl.entity.TradingEntity;
import com.sigma429.sl.enums.PayChannelEnum;
import com.sigma429.sl.enums.TradingEnum;
import com.sigma429.sl.enums.TradingStateEnum;
import com.sigma429.sl.exception.SLException;
import com.sigma429.sl.handler.BeforePayHandler;
import com.sigma429.sl.handler.HandlerFactory;
import com.sigma429.sl.handler.NativePayHandler;
import com.sigma429.sl.service.NativePayService;
import com.sigma429.sl.service.QRCodeService;
import com.sigma429.sl.service.TradingService;
import com.sigma429.sl.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:NativePayServiceImpl
 * Package:com.sigma429.sl.service.impl
 * Description:Native支付方式Face接口：商户生成二维码，用户扫描支付
 * @Author:14亿少女的梦-Sigma429
 * @Create:2024/03/24 - 19:10
 * @Version:v1.0
 */
@Service
@Slf4j
public class NativePayServiceImpl implements NativePayService {
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private TradingService tradingService;
    @Resource
    private BeforePayHandler beforePayHandler;
    @Resource
    private QRCodeService qrCodeService;

    @Override
    public String queryQrCodeUrl(Long tradingOrderNo) {
        TradingEntity trading = tradingService.findTradByTradingOrderNo(tradingOrderNo);
        if (ObjectUtil.equals(trading.getTradingState(), TradingStateEnum.YJS)) {
            // 订单已完成，不返回二维码
            throw new SLException(TradingEnum.TRADING_STATE_SUCCEED);
        }
        return trading.getQrCode();
    }

    @Override
    public TradingEntity createDownLineTrading(TradingEntity tradingEntity) {
        // 交易前置处理：检测交易单参数
        Boolean flag = beforePayHandler.checkCreateTrading(tradingEntity);
        if (!flag) {
            throw new SLException(TradingEnum.CONFIG_ERROR);
        }

        tradingEntity.setTradingType(TradingConstant.TRADING_TYPE_FK);
        tradingEntity.setEnableFlag(Constant.YES);

        // 对交易订单加锁
        Long productOrderNo = tradingEntity.getProductOrderNo();
        String key = TradingCacheConstant.CREATE_PAY + productOrderNo;
        RLock lock = redissonClient.getFairLock(key);
        try {
            // 获取锁
            if (lock.tryLock(TradingCacheConstant.REDIS_WAIT_TIME, TimeUnit.SECONDS)) {
                // 交易前置处理：幂等性处理
                beforePayHandler.idempotentCreateTrading(tradingEntity);

                // 调用不同的支付渠道进行处理
                PayChannelEnum payChannel = PayChannelEnum.valueOf(tradingEntity.getTradingChannel());
                NativePayHandler nativePayHandler = HandlerFactory.get(payChannel, NativePayHandler.class);
                nativePayHandler.createDownLineTrading(tradingEntity);

                // 生成统一收款二维码
                String placeOrderMsg = tradingEntity.getPlaceOrderMsg();
                String qrCode = qrCodeService.generate(placeOrderMsg, payChannel);
                tradingEntity.setQrCode(qrCode);

                // 新增或更新交易数据
                flag = tradingService.saveOrUpdate(tradingEntity);
                if (!flag) {
                    throw new SLException(TradingEnum.SAVE_OR_UPDATE_FAIL);
                }
                return tradingEntity;
            }
            throw new SLException(TradingEnum.NATIVE_PAY_FAIL);
        } catch (SLException e) {
            throw e;
        } catch (Exception e) {
            log.error("统一收单线下交易预创建异常:{}", ExceptionUtil.stacktraceToString(e));
            throw new SLException(TradingEnum.NATIVE_PAY_FAIL);
        } finally {
            lock.unlock();
        }
    }
}
