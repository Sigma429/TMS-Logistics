package com.sigma429.sl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sigma429.sl.entity.OrderCargoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 货品总重量  Mapper 接口
 */
@Mapper
public interface OrderCargoMapper extends BaseMapper<OrderCargoEntity> {
}
