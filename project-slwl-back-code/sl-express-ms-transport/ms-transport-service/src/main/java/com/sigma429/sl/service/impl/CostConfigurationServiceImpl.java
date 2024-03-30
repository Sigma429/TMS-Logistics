package com.sigma429.sl.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.sigma429.sl.domain.CostConfigurationDTO;
import com.sigma429.sl.enums.ExceptionEnum;
import com.sigma429.sl.enums.TransportLineEnum;
import com.sigma429.sl.exception.SLException;
import com.sigma429.sl.service.CostConfigurationService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 成本配置相关业务
 */
@Service
public class CostConfigurationServiceImpl implements CostConfigurationService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 成本配置 redis key
     */
    static final String SL_TRANSPORT_COST_REDIS_KEY = "SL_TRANSPORT_COST_CONFIGURATION";

    /**
     * 默认成本配置
     */
    static final Map<Object, Object> DEFAULT_COST = Map.of(TransportLineEnum.TRUNK_LINE.getCode(), 0.8,
            TransportLineEnum.BRANCH_LINE.getCode(), 1.2, TransportLineEnum.CONNECT_LINE.getCode(), 1.5);

    /**
     * 查询成本配置
     * @return 成本配置
     */
    @Override
    public List<CostConfigurationDTO> findConfiguration() {
        return null;
    }

    /**
     * 保存成本配置
     * @param dto 成本配置
     */
    @Override
    public void saveConfiguration(List<CostConfigurationDTO> dto) {

    }

    /**
     * 查询成本根据类型
     * @param type 类型
     * @return 成本
     */
    @Override
    public Double findCostByType(Integer type) {
        return null;
    }
}
