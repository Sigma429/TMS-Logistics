package com.sigma429.sl.entity.truck;

import com.baomidou.mybatisplus.annotation.TableName;
import com.sigma429.sl.entity.BaseEntity;
import com.sigma429.sl.enums.TruckPlanScheduleStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 车次与车辆关联表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sl_truck_plan")
public class TruckPlanEntity extends BaseEntity {

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
    private String driverIds;

    /**
     * 计划发车时间
     */
    private LocalDateTime planDepartureTime;

    /**
     * 计划到达时间
     */
    private LocalDateTime planArrivalTime;

    /**
     * 状态
     */
    private Integer status;

    /**
     * @see TruckPlanScheduleStatusEnum
     */
    private Integer scheduleStatus;
}
