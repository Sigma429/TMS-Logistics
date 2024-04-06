package com.sigma429.sl.controller.transport;

import com.sigma429.sl.service.TransportService;
import com.sigma429.sl.vo.R;
import com.sigma429.sl.vo.work.CostConfigurationVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 调度配置管理
 */
@Api(tags = "成本配置")
@Slf4j
@RestController
@RequestMapping("cost-configuration-manager")
public class CostConfigurationController {
    @Resource
    private TransportService transportService;

    @ApiOperation(value = "保存成本配置")
    @PostMapping
    public R<Void> saveConfiguration(@Valid @RequestBody List<CostConfigurationVO> vo) {
        transportService.saveCostConfiguration(vo);
        return R.success();
    }

    @ApiOperation(value = "查询成本配置")
    @GetMapping
    public R<List<CostConfigurationVO>> findConfiguration() {
        return R.success(transportService.findCostConfiguration());
    }
}
