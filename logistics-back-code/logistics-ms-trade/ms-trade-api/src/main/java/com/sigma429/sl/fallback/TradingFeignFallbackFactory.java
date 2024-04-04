package com.sigma429.sl.fallback;

import com.sigma429.sl.TradingFeign;
import com.sigma429.sl.domain.TradingDTO;
import org.springframework.cloud.openfeign.FallbackFactory;


public class TradingFeignFallbackFactory implements FallbackFactory<TradingFeign> {
    @Override
    public TradingFeign create(Throwable cause) {
        return new TradingFeign() {
            @Override
            public TradingDTO queryTrading(Long productOrderNo, Long tradingOrderNo) {
                return null;
            }
        };
    }
}
