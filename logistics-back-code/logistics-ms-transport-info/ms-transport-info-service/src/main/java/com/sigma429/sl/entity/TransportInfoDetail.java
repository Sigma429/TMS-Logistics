package com.sigma429.sl.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 物流信息详情
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransportInfoDetail {
    // 创建时间，时间戳
    private Long created;
    // 详细信息，例如：您的快件已到达【北京通州分拣中心】
    private String info;
    // 状态，例如：运输中
    private String status;
}
