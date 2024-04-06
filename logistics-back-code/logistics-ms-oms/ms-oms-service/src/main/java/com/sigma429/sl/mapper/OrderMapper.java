package com.sigma429.sl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sigma429.sl.entity.OrderEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单状态  Mapper 接口
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderEntity> {

}
