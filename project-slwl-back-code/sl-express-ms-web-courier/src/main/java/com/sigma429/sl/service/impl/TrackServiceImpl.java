package com.sigma429.sl.service.impl;

import cn.hutool.core.collection.CollUtil;

import com.sigma429.sl.PickupDispatchTaskFeign;
import com.sigma429.sl.TrackFeign;
import com.sigma429.sl.TransportOrderFeign;
import com.sigma429.sl.service.TrackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrackServiceImpl implements TrackService {
    @Resource
    private TrackFeign trackFeign;
    @Resource
    private PickupDispatchTaskFeign pickupDispatchTaskFeign;
    @Resource
    private TransportOrderFeign transportOrderFeign;

    /**
     * 快递员上报位置
     *
     * @param lng 经度
     * @param lat 纬度
     */
    @Override
    public Boolean uploadLocation(String lng, String lat) {
        return null;
    }
}
