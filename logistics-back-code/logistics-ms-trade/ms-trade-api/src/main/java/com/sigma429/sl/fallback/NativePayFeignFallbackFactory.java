package com.sigma429.sl.fallback;

import com.sigma429.sl.NativePayFeign;
import com.sigma429.sl.domain.request.NativePayDTO;
import com.sigma429.sl.domain.response.NativePayResponseDTO;
import org.springframework.cloud.openfeign.FallbackFactory;


public class NativePayFeignFallbackFactory implements FallbackFactory<NativePayFeign> {
    @Override
    public NativePayFeign create(Throwable cause) {
        return new NativePayFeign() {
            @Override
            public NativePayResponseDTO createDownLineTrading(NativePayDTO nativePayDTO) {
                return null;
            }

            @Override
            public String queryQrCode(Long tradingOrderNo) {
                return null;
            }
        };
    }
}
