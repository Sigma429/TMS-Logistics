package com.sigma429.sl.service;


import com.sigma429.sl.domain.CostConfigurationDTO;

import java.util.List;

/**
 * 成本配置相关业务
 */
public interface CostConfigurationService {
    /**
     * 查询成本配置
     *
     * @return 成本配置
     */
    List<CostConfigurationDTO> findConfiguration();

    /**
     * 保存成本配置
     * @param dto 成本配置
     */
    void saveConfiguration(List<CostConfigurationDTO> dto);

    /**
     * 查询成本根据类型
     * @param type 类型
     * @return 成本
     */
    Double findCostByType(Integer type);
}
