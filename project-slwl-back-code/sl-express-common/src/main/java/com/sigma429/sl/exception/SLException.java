package com.sigma429.sl.exception;

import com.sigma429.sl.enums.BaseEnum;
import com.sigma429.sl.enums.BaseExceptionEnum;
import lombok.Data;

/**
 * 自定义异常
 */
@Data
public class SLException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    // 异常中的信息
    private String msg;
    // 业务状态码，规则：4位数，从1001开始递增
    private int code = 1001;
    // http状态码，按照http协议规范，如：200,201,400等
    private int status = 500;

    public SLException(BaseEnum baseEnum) {
        super(baseEnum.getValue());
        this.msg = baseEnum.getValue();
        this.code = baseEnum.getCode();
    }

    public SLException(BaseEnum baseEnum, Throwable e) {
        super(baseEnum.getValue(), e);
        this.msg = baseEnum.getValue();
        this.code = baseEnum.getCode();
    }

    public SLException(BaseExceptionEnum errorEnum) {
        super(errorEnum.getValue());
        this.status = errorEnum.getStatus();
        this.msg = errorEnum.getValue();
        this.code = errorEnum.getCode();
    }

    public SLException(BaseExceptionEnum errorEnum, Throwable e) {
        super(errorEnum.getValue(), e);
        this.status = errorEnum.getStatus();
        this.msg = errorEnum.getValue();
        this.code = errorEnum.getCode();
    }

    public SLException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public SLException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public SLException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public SLException(String msg, int code, int status) {
        super(msg);
        this.msg = msg;
        this.code = code;
        this.status = status;
    }

    public SLException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public SLException(String msg, int code, int status, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
        this.status = status;
    }

}
