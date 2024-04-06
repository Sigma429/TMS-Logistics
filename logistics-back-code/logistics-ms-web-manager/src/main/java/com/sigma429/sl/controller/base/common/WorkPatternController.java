package com.sigma429.sl.controller.base.common;

import com.sigma429.sl.base.WorkPatternAddDTO;
import com.sigma429.sl.base.WorkPatternDTO;
import com.sigma429.sl.base.WorkPatternQueryDTO;
import com.sigma429.sl.base.WorkPatternUpdateDTO;
import com.sigma429.sl.service.BaseCommonService;
import com.sigma429.sl.util.PageResponse;
import com.sigma429.sl.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 工作模式管理
 */
@RequestMapping("work-patterns")
@RestController
@Slf4j
@Api(tags = "工作模式相关接口")
public class WorkPatternController {

    @Resource
    private BaseCommonService baseCommonService;

    @GetMapping("all")
    @ApiOperation("工作模式列表查询")
    public R<Map<Byte, List<WorkPatternDTO>>> list() {
        return R.success(baseCommonService.listWorkPattern());
    }

    @GetMapping("page")
    @ApiOperation("工作模式分页查询")
    public R<PageResponse<WorkPatternDTO>> list(WorkPatternQueryDTO workPatternQueryDTO) {
        log.info("workPatternQueryDTO : {}", workPatternQueryDTO);
        return R.success(baseCommonService.listWorkPattern(workPatternQueryDTO));
    }

    @GetMapping("{id}")
    @ApiOperation("根据工作模式id获取工作模式详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "工作模式id")
    })
    public R<WorkPatternDTO> getById(@PathVariable("id") Long id) {
        return R.success(baseCommonService.getWorkPatternById(id));
    }

    @DeleteMapping("{id}")
    @ApiOperation("工作模式删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "工作模式id")
    })
    public R<Void> delete(@PathVariable("id") Long id) {
        baseCommonService.deleteWorkPattern(id);
        return R.success();
    }

    @PostMapping
    @ApiOperation("新增工作模式")
    public R<Void> add(@RequestBody WorkPatternAddDTO workPatternAddDTO) {
        baseCommonService.addWorkPattern(workPatternAddDTO);
        return R.success();
    }

    @PutMapping
    @ApiOperation("修改工作模式")
    public R<Void> put(@RequestBody WorkPatternUpdateDTO workPatternUpdateDTO) {
        baseCommonService.putWorkPattern(workPatternUpdateDTO);
        return R.success();
    }

}
