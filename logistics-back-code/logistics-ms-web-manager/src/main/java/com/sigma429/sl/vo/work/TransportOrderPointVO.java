package com.sigma429.sl.vo.work;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel("订单跟踪")
public class TransportOrderPointVO {
    @ApiModelProperty("创建时间")
    private LocalDateTime created;

    @ApiModelProperty(value = "详细信息", example = "例如：您的快件已到达【北京通州分拣中心】", required = true)
    private String info;

    @ApiModelProperty(value = "状态，", example = "例如：运输中", required = true)
    private String status;

}
