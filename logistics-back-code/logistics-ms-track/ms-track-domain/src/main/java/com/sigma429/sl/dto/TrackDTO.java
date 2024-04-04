package com.sigma429.sl.dto;

import com.sigma429.sl.enums.TrackStatusEnum;
import com.sigma429.sl.enums.TrackTypeEnum;
import lombok.Data;

import java.util.List;

@Data
public class TrackDTO {

    /**
     * 运单id
     */
    private String transportOrderId;

    /**
     * 轨迹坐标点列表
     */
    private List<MarkerPointDTO> pointList;

    /**
     * 距离，单位：米
     */
    private Double distance;

    /**
     * 最新的位置坐标，x：经度，y：纬度
     */
    private MarkerPointDTO lastPoint;

    /**
     * 状态
     */
    private TrackStatusEnum status;

    /**
     * 类型
     */
    private TrackTypeEnum type;

    /**
     * 创建时间
     */
    private Long created;

    /**
     * 更新时间
     */
    private Long updated;
}
