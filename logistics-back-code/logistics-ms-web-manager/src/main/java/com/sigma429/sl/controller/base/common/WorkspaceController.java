package com.sigma429.sl.controller.base.common;

import com.sigma429.sl.service.WorkspaceService;
import com.sigma429.sl.vo.R;
import com.sigma429.sl.vo.workspace.MonthlyOrderAddVO;
import com.sigma429.sl.vo.workspace.WorkbenchAddVO;
import com.sigma429.sl.vo.workspace.WorkbenchVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 工作台相关
 */
@Api(tags = "工作台")
@RestController
@Slf4j
@RequestMapping("/workspace")
public class WorkspaceController {

    @Resource
    private WorkspaceService workspaceService;

    @ApiOperation("新增数据")
    @PostMapping
    public R<Void> saveWorkbench(@RequestBody WorkbenchAddVO workbenchVO) {
        workspaceService.saveWorkbench(workbenchVO);
        return R.success();
    }

    @ApiOperation("新增订单总量数据")
    @PostMapping("saveOrderNumber")
    public R<Void> saveOrderNumber(@RequestBody List<MonthlyOrderAddVO> monthlyOrderList) {
        workspaceService.saveOrderNumber(monthlyOrderList);
        return R.success();
    }

    @ApiOperation("查询一条数据")
    @GetMapping
    public R<WorkbenchVO> findOne() {
        return R.success(workspaceService.findOne());
    }

    @ApiOperation("删除所有")
    @DeleteMapping("deleteAll")
    public R<Void> deleteAll() {
        workspaceService.deleteAll();
        return R.success();
    }
}
