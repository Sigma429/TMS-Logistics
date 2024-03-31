package com.sigma429.sl.controller;


import com.sigma429.sl.service.AreaService;
import com.sigma429.sl.vo.R;
import com.sigma429.sl.vo.area.AreaSimpleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "地址相关接口")
@RestController
@RequestMapping("/areas")
public class AreaController {

    @Resource
    private AreaService areaService;

    @ApiOperation("根据父id获取地址信息")
    @GetMapping("/children")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", value = "行政区域父id", example = "0")
    })
    public R<List<AreaSimpleVO>> findChildrenAreaByParentId(@RequestParam(value = "parentId", required = false,
            defaultValue = "0") Long parentId) {
        return R.success(areaService.findChildrenAreaByParentId(parentId));
    }
}
