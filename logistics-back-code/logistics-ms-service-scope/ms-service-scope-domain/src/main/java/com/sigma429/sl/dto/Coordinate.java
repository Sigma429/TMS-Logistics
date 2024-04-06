package com.sigma429.sl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coordinate {
    // 经度
    private Double longitude;
    // 纬度
    private Double latitude;
}