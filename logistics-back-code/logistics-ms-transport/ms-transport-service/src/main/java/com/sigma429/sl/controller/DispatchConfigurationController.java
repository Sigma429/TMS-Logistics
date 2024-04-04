package com.sigma429.sl.controller;

import com.sigma429.sl.domain.DispatchConfigurationDTO;
import com.sigma429.sl.service.DispatchConfigurationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 调度配置相关业务对外提供接口服务
 */
@Api(tags = "调度配置")
@RequestMapping("dispatch-configuration")
@Validated
@RestController
public class DispatchConfigurationController {
    @Resource
    private DispatchConfigurationService dispatchConfigurationService;

    @ApiOperation(value = "查询调度配置")
    @GetMapping
    public DispatchConfigurationDTO findConfiguration() {
        return dispatchConfigurationService.findConfiguration();
    }

    @ApiOperation(value = "保存调度配置")
    @PostMapping
    public void saveConfiguration(@RequestBody DispatchConfigurationDTO dto) {
        dispatchConfigurationService.saveConfiguration(dto);
    }
}
