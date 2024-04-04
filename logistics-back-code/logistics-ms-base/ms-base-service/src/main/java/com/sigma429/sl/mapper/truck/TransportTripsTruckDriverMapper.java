package com.sigma429.sl.mapper.truck;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sigma429.sl.entity.truck.TransportTripsTruckDriverEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 车次与车辆关联信息表  Mapper 接口
 */
@Mapper
public interface TransportTripsTruckDriverMapper extends BaseMapper<TransportTripsTruckDriverEntity> {

}
