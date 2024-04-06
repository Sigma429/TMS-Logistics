package com.sigma429.sl.vo.baseTruck;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 回车登记列表元素
 */
@Data
@ApiModel(value = "回车登记列表元素")
public class TruckReturnRegisterListVO {

    @ApiModelProperty(value = "回车登记id")
    private Long id;

    @ApiModelProperty(value = "车辆id")
    private Long truckId;

    @ApiModelProperty(value = "车牌号码")
    private String licensePlate;

    @ApiModelProperty(value = "运输任务id")
    private Long transportTaskId;

    @ApiModelProperty(value = "起始机构id")
    private Long startAgencyId;

    @ApiModelProperty(value = "起始机构名称")
    private String startAgencyName;

    @ApiModelProperty(value = "结束机构id")
    private Long endAgencyId;

    @ApiModelProperty(value = "结束机构名称")
    private String endAgencyName;

    @ApiModelProperty(value = "运单数量")
    private Integer transportOrderNumber;

    @ApiModelProperty(value = "出车时间", example = "2022-09-12 15:16:00")
    private LocalDateTime outStorageTime;

    @ApiModelProperty(value = "回车时间", example = "2022-09-12 15:37:00")
    private LocalDateTime intoStorageTime;

    @ApiModelProperty(value = "车辆是否可用")
    private Boolean isAvailable;
}
