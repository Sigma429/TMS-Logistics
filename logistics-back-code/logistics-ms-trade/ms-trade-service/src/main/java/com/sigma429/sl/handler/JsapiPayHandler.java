package com.sigma429.sl.handler;


import com.sigma429.sl.entity.TradingEntity;

/**
 * jsapi下单处理
 */
public interface JsapiPayHandler {

    /**
     * 创建交易
     *
     * @param tradingEntity 交易单
     */
    void createJsapiTrading(TradingEntity tradingEntity);
}
