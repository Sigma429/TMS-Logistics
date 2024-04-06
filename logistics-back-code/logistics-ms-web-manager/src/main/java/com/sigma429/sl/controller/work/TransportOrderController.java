package com.sigma429.sl.controller.work;

import com.sigma429.sl.service.WorkService;
import com.sigma429.sl.util.PageResponse;
import com.sigma429.sl.vo.R;
import com.sigma429.sl.vo.work.TrackVO;
import com.sigma429.sl.vo.work.TransportOrderQueryVO;
import com.sigma429.sl.vo.work.TransportOrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 运单管理
 */
@Slf4j
@Api(tags = "运单相关")
@RestController
@RequestMapping("transport-order-manager")
public class TransportOrderController {

    @Resource
    private WorkService workService;

    @ApiOperation(value = "获取运单分页数据")
    @PostMapping("/page")
    public R<PageResponse<TransportOrderVO>> findByPage(@RequestBody TransportOrderQueryVO vo) {
        return R.success(workService.findTransportOrderByPage(vo));
    }

    @ApiOperation(value = "获取运单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "运单id", required = true, example = "1")
    })
    @GetMapping("/{id}")
    public R<TransportOrderVO> findById(@PathVariable(name = "id") String id) {
        return R.success(workService.findTransportOrderDetail(id));
    }

    @ApiOperation(value = "统计运单")
    @GetMapping("/count")
    public R<Map<Integer, Long>> count() {
        return R.success(workService.countTransportOrder());
    }

    @ApiOperation(value = "获取运单轨迹详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "运单id", required = true, example = "1")
    })
    @GetMapping("/track/{id}")
    public R<TrackVO> findTrackById(@PathVariable(name = "id") String id) {
        return R.success(workService.findTrackById(id));
    }
}
