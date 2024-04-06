package com.sigma429.sl.enums;

import cn.hutool.core.util.EnumUtil;

/**
 * 服务类型枚举
 */
public enum ServiceTypeEnum implements BaseEnum {

    ORGAN(1, "机构"),
    COURIER(2, "快递员");

    /**
     * 类型编码
     */
    private final Integer code;

    /**
     * 类型值
     */
    private final String value;


    ServiceTypeEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getValue() {
        return value;
    }

    public static ServiceTypeEnum codeOf(Integer code) {
        return EnumUtil.getBy(ServiceTypeEnum::getCode, code);
    }
}
