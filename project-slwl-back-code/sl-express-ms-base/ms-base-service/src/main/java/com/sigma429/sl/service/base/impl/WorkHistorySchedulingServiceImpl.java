package com.sigma429.sl.service.base.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sigma429.sl.entity.base.WorkHistorySchedulingEntity;
import com.sigma429.sl.mapper.base.WorkHistorySchedulingMapper;
import com.sigma429.sl.service.base.WorkHistorySchedulingService;
import org.springframework.stereotype.Service;

@Service
public class WorkHistorySchedulingServiceImpl extends ServiceImpl<WorkHistorySchedulingMapper,
        WorkHistorySchedulingEntity> implements WorkHistorySchedulingService {

}
