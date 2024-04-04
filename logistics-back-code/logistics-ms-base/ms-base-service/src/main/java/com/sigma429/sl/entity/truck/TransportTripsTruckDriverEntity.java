package com.sigma429.sl.entity.truck;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sigma429.sl.entity.BaseEntity;
import lombok.Data;

/**
 * 车次与车辆关联表
 */
@Data
@TableName("sl_truck_trips_truck_driver")
public class TransportTripsTruckDriverEntity extends BaseEntity {
    private static final long serialVersionUID = 2060686653575483040L;
    /**
     * 车辆id
     */
    private Long truckId;
    /**
     * 车次id
     */
    private Long transportTripsId;
    /**
     * 司机id
     */
    private Long driverId;
}
