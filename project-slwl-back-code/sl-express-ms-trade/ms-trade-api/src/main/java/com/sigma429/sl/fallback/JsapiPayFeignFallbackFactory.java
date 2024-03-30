package com.sigma429.sl.fallback;

import com.sigma429.sl.JsapiPayFeign;
import com.sigma429.sl.domain.request.JsapiPayDTO;
import com.sigma429.sl.domain.response.JsapiPayResponseDTO;
import org.springframework.cloud.openfeign.FallbackFactory;

public class JsapiPayFeignFallbackFactory implements FallbackFactory<JsapiPayFeign> {
    @Override
    public JsapiPayFeign create(Throwable cause) {
        return new JsapiPayFeign() {
            @Override
            public JsapiPayResponseDTO createJsapiTrading(JsapiPayDTO jsapiPayDTO) {
                return null;
            }
        };
    }
}
