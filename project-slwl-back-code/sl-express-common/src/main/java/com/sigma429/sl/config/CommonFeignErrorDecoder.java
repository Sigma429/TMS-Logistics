package com.sigma429.sl.config;


import com.sigma429.sl.exception.SLException;

/**
 * 通用解码器实现
 */
public class CommonFeignErrorDecoder extends FeignErrorDecoder {

    @Override
    public Exception call(int status, int code, String msg) {
        return new SLException(msg, code, status);
    }
}
