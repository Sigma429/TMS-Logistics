package com.sigma429.sl.vo;

import cn.hutool.core.annotation.Alias;
import lombok.Data;

@Data
public class AuthUserInfo extends BaseMsg{

    /**
     * 用户id
     */
    @Alias("sub")
    private Long userId;
    /**
     * 用户名
     */
    private String account;
    /**
     * 姓名
     */
    private String name;
    /**
     * 机构id
     */
    @Alias("orgid")
    private Long orgId;
    /**
     * 岗位
     */
    @Alias("stationid")
    private Long stationId;

    /**
     * 是否为管理员
     */
    private Boolean administrator;

}
