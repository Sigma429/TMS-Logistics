package com.sigma429.sl.service.impl;

import com.sigma429.sl.entity.TrackEntity;
import com.sigma429.sl.service.TrackService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName:TrackServiceImpl
 * Package:com.sigma429.sl.service
 * Description:
 * @Author:14亿少女的梦-Sigma429
 * @Create:2024/04/09 - 18:26
 * @Version:v1.0
 */
@Service
public class TrackServiceImpl implements TrackService {
    @Override
    public boolean create(String transportOrderId) {
        return false;
    }

    @Override
    public boolean complete(List<String> transportOrderIds) {
        return false;
    }

    @Override
    public TrackEntity queryByTransportOrderId(String transportOrderId) {
        return null;
    }

    @Override
    public boolean uploadFromTruck(Long transportTaskId, double lng, double lat) {
        return false;
    }

    @Override
    public boolean uploadFromCourier(List<String> transportOrderIds, double lng, double lat) {
        return false;
    }
}
