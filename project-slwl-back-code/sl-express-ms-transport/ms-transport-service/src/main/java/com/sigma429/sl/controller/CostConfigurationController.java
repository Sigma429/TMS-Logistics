package com.sigma429.sl.controller;

import com.sigma429.sl.domain.CostConfigurationDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 成本配置相关业务对外提供接口服务
 */
@Api(tags = "成本配置")
@RequestMapping("cost-configuration")
@Validated
@RestController
public class CostConfigurationController {
    @Resource
    private CostConfigurationService costConfigurationService;

    @ApiOperation(value = "查询成本配置")
    @GetMapping
    public List<CostConfigurationDTO> findConfiguration() {
        return costConfigurationService.findConfiguration();
    }

    @ApiOperation(value = "保存成本配置")
    @PostMapping
    public void saveConfiguration(@RequestBody List<CostConfigurationDTO> dto) {
        costConfigurationService.saveConfiguration(dto);
    }
}
