package com.sigma429.sl.entity.line;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 运输路线实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransportLine {

    private Long id;
    // 编号
    private String number;
    // 成本
    private Double cost;
    // 类型
    private Integer type;
    // 路线名称
    private String name;
    // 距离，单位：米
    private Double distance;
    // 时间，单位：秒
    private Long time;
    // 创建时间
    private Long created;
    // 修改时间
    private Long updated;
    // 扩展字段，以json格式存储
    private String extra;
    // 起点机构id
    private Long startOrganId;
    // 起点机构名称，只有在查询时返回
    private String startOrganName;
    // 终点机构id
    private Long endOrganId;
    // 终点机构名称，只有在查询时返回
    private String endOrganName;
}
