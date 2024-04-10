package com.sigma429.sl.config;

import com.sigma429.sl.exception.SLWebException;
import org.springframework.context.annotation.Configuration;

/**
 * web调用feign失败解码器实现
 */
@Configuration
public class WebFeignErrorDecoder extends FeignErrorDecoder {

    @Override
    public Exception call(int status, int code, String msg) {
        return new SLWebException(msg);
    }
}
