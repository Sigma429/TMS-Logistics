package com.sigma429.sl.enums;

import cn.hutool.core.util.EnumUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 短信类型枚举
 */
public enum SmsTypeEnum implements BaseEnum {

    VERIFY(1, "验证类型短信"),
    NOTICE(2, "通知类型短信");

    @EnumValue
    @JsonValue
    private Integer code;
    private String value;

    SmsTypeEnum(Integer code, String value) {
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

    public static SmsTypeEnum codeOf(Integer code) {
        return EnumUtil.getBy(SmsTypeEnum::getCode, code);
    }
}
