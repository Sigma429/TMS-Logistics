package com.sigma429.sl.service.impl;

import com.sigma429.sl.dto.CourierTaskDTO;
import com.sigma429.sl.dto.CourierTaskPageQueryDTO;
import com.sigma429.sl.entity.CourierTaskEntity;
import com.sigma429.sl.service.CourierTaskService;
import com.sigma429.sl.util.PageResponse;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName:CourierTaskServiceImpl
 * Package:com.sigma429.sl.service.impl
 * Description:
 * @Author:14亿少女的梦-Sigma429
 * @Create:2024/04/09 - 18:26
 * @Version:v1.0
 */
@Service
public class CourierTaskServiceImpl implements CourierTaskService {
    @Override
    public PageResponse<CourierTaskDTO> pageQuery(CourierTaskPageQueryDTO pageQueryDTO) {
        return null;
    }

    @Override
    public void saveOrUpdate(CourierTaskDTO courierTaskDTO) {

    }

    @Override
    public CourierTaskDTO findById(Long id) {
        return null;
    }

    @Override
    public List<CourierTaskEntity> findByOrderId(Long orderId) {
        return null;
    }
}
