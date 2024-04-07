package com.sigma429.sl.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.sigma429.sl.dto.MarkerPointDTO;
import com.sigma429.sl.dto.TrackDTO;
import com.sigma429.sl.entity.TrackEntity;
import org.springframework.data.mongodb.core.geo.GeoJsonLineString;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.List;
import java.util.stream.Collectors;

public class TrackEntityUtil {

    public static TrackDTO toDTO(TrackEntity trackEntity) {
        CopyOptions copyOptions =
                CopyOptions.create().setIgnoreProperties("planGeoJsonLine", "lastPoint").ignoreNullValue();
        TrackDTO trackDTO = BeanUtil.toBean(trackEntity, TrackDTO.class, copyOptions);
        // 转化轨迹点
        GeoJsonLineString planGeoJsonLine = trackEntity.getPlanGeoJsonLine();
        if (ObjectUtil.isAllNotEmpty(planGeoJsonLine, planGeoJsonLine.getCoordinates())) {
            List<MarkerPointDTO> coordinateList = planGeoJsonLine.getCoordinates().stream()
                    .map(point -> new MarkerPointDTO(point.getX(), point.getY()))
                    .collect(Collectors.toList());
            trackDTO.setPointList(coordinateList);
        }

        // 转化最新位置坐标
        if (ObjectUtil.isNotEmpty(trackEntity.getLastPoint())) {
            GeoJsonPoint point = trackEntity.getLastPoint();
            trackDTO.setLastPoint(new MarkerPointDTO(point.getX(), point.getY()));
        }
        return trackDTO;
    }

}
