package com.sigma429.sl.enums;

import cn.hutool.core.util.EnumUtil;

/**
 * 支付渠道枚举
 */
public enum PayChannelEnum implements BaseEnum {

    ALI_PAY(1, "支付宝"),
    WECHAT_PAY(2, "微信支付");

    private Integer code;
    private String value;

    PayChannelEnum(Integer code, String value) {
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

    public static PayChannelEnum codeOf(Integer code) {
        return EnumUtil.getBy(PayChannelEnum::getCode, code);
    }
}
