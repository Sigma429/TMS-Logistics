package com.sigma429.sl.dto.response;

import com.sigma429.sl.enums.transportorder.TransportOrderStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransportOrderStatusCountDTO {

    @ApiModelProperty(value = "状态枚举", required = true)
    private TransportOrderStatus status;
    @ApiModelProperty(value = "状态编码", required = true)
    private Integer statusCode;
    @ApiModelProperty(value = "数量", required = true)
    private Long count;

}
