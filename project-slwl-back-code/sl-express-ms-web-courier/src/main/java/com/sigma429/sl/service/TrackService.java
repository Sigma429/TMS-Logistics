package com.sigma429.sl.service;

public interface TrackService {

    /**
     * 快递员上报位置
     *
     * @param lng 经度
     * @param lat 纬度
     * @return 是否成功
     */
    Boolean uploadLocation(String lng, String lat);
}
