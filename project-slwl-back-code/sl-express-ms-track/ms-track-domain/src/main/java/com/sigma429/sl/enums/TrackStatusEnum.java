package com.sigma429.sl.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 轨迹状态
 */
public enum TrackStatusEnum implements BaseEnum {

    NEW(1, "新建"),
    COMPLETE(4, "完成");

    @JsonValue
    private Integer code;
    private String value;

    TrackStatusEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
