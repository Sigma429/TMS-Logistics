package com.sigma429.sl.enums;


public enum IdEnum implements BaseEnum {

    TRANSPORT_ORDER(1, "运单号", "transport_order", "segment", "SL");

    private Integer code;
    private String value;
    // 业务名称
    private String biz;
    // 类型：自增长（segment），雪花id（snowflake）
    private String type;
    // id前缀
    private String prefix;

    IdEnum(Integer code, String value, String biz, String type, String prefix) {
        this.code = code;
        this.value = value;
        this.biz = biz;
        this.type = type;
        this.prefix = prefix;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public String getBiz() {
        return biz;
    }

    public String getType() {
        return type;
    }

    public String getPrefix() {
        return prefix;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("IdEnum{");
        sb.append("code=").append(code);
        sb.append(", value='").append(value).append('\'');
        sb.append(", biz='").append(biz).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", prefix='").append(prefix).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
