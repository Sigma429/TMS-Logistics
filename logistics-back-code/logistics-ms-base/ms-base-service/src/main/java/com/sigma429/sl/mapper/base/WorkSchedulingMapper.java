package com.sigma429.sl.mapper.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sigma429.sl.entity.base.WorkSchedulingEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WorkSchedulingMapper extends BaseMapper<WorkSchedulingEntity> {

    void batchInsert(@Param("entities") List<WorkSchedulingEntity> workSchedulingEntities);
}
