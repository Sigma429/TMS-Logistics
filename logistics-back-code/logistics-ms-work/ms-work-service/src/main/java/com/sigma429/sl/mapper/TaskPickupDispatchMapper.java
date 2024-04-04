package com.sigma429.sl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sigma429.sl.dto.CourierTaskCountDTO;
import com.sigma429.sl.entity.PickupDispatchTaskEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 取件、派件任务信息表 Mapper 接口
 */
@Mapper
public interface TaskPickupDispatchMapper extends BaseMapper<PickupDispatchTaskEntity> {

    List<CourierTaskCountDTO> findCountByCourierIds(@Param("courierIds") List<Long> courierIds,
                                                    @Param("type") Integer type,
                                                    @Param("startDateTime") LocalDateTime startDateTime,
                                                    @Param("endDateTime") LocalDateTime endDateTime);
}
