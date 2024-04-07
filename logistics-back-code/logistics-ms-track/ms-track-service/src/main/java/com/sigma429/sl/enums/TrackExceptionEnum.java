package com.sigma429.sl.enums;

import cn.hutool.core.util.EnumUtil;

/**
 * 异常枚举
 */
public enum TrackExceptionEnum implements BaseExceptionEnum {

    TRACK_ALREADY_EXISTS(1001, "轨迹已经存在");

    private Integer code;
    private Integer status;
    private String value;

    TrackExceptionEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
        this.status = 500;
    }

    TrackExceptionEnum(Integer code, Integer status, String value) {
        this.code = code;
        this.value = value;
        this.status = status;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public Integer getStatus() {
        return this.status;
    }

    public static TrackExceptionEnum codeOf(Integer code) {
        return EnumUtil.getBy(TrackExceptionEnum::getCode, code);
    }
}
