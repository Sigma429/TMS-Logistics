package com.sigma429.sl.controller;

import com.sigma429.sl.service.BaseService;
import com.sigma429.sl.vo.R;
import com.sigma429.sl.vo.base.AreaSimpleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 行政机构
 * 用于地址选择 省市县三级行政机构
 */
@Api(tags = "行政机构")
@RestController
@Slf4j
@RequestMapping("/areas")
public class AreaController {

    @Resource
    private BaseService baseService;

    @ApiOperation("根据父id获取地址信息")
    @GetMapping("/children")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", value = "行政区域父id", required = false, example = "0")
    })
    public R<List<AreaSimpleVO>> findChildrenAreaByParentId(@RequestParam(value = "parentId", required = false,
            defaultValue = "0") Long parentId) {
        return R.success(baseService.findChildrenAreaByParentId(parentId));
    }
}
