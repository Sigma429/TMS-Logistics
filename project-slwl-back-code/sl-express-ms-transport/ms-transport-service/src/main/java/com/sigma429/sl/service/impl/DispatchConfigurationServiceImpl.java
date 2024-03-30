package com.sigma429.sl.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.sigma429.sl.domain.DispatchConfigurationDTO;
import com.sigma429.sl.service.DispatchConfigurationService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 调度服务相关业务
 */
@Service
public class DispatchConfigurationServiceImpl implements DispatchConfigurationService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 调度时间配置
     */
    static final String DISPATCH_TIME_REDIS_KEY = "DISPATCH_CONFIGURATION:TIME";

    /**
     * 调度方式配置
     */
    static final String DISPATCH_METHOD_REDIS_KEY = "DISPATCH_CONFIGURATION:METHOD";

    @Override
    public DispatchConfigurationDTO findConfiguration() {
        return null;
    }

    @Override
    public void saveConfiguration(DispatchConfigurationDTO dto) {

    }
}
