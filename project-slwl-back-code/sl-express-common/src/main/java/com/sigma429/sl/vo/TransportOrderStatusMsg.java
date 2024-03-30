package com.sigma429.sl.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * 运单状态更新消息
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TransportOrderStatusMsg extends BaseMsg {

    /**
     * 运单号列表
     */
    private List<String> idList;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 状态编码
     */
    private Integer statusCode;

}
