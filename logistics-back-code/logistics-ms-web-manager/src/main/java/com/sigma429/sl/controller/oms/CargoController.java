package com.sigma429.sl.controller.oms;


import com.sigma429.sl.service.OmsService;
import com.sigma429.sl.vo.R;
import com.sigma429.sl.vo.oms.OrderCargoUpdateVO;
import com.sigma429.sl.vo.oms.OrderCargoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 货品管理
 */
@RestController
@RequestMapping("order-manager/cargo")
@Api(tags = "货品管理")
@Slf4j
public class CargoController {

    @Resource
    private OmsService omsService;

    @ApiOperation(value = "添加货物")
    @PostMapping
    public R<OrderCargoUpdateVO> saveOrderCargo(@RequestBody OrderCargoUpdateVO vo) {
        omsService.save(vo);
        return R.success();
    }

    @ApiOperation(value = "获取货物列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单id", required = true, example = "0")
    })
    @GetMapping
    public R<List<OrderCargoVO>> findAll(@RequestParam("orderId") Long orderId) {
        return R.success(omsService.findAll(null, orderId));
    }

    @ApiOperation(value = "更新货物信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "货物id", required = true, example = "1")
    })
    @PutMapping("/{id}")
    public R<OrderCargoUpdateVO> updateOrderCargo(@PathVariable("id") Long id, @RequestBody OrderCargoUpdateVO vo) {
        omsService.update(id, vo);
        return R.success();
    }

    @ApiOperation(value = "删除货物")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "货物id", required = true, example = "1")
    })
    @DeleteMapping("/{id}")
    public R<OrderCargoVO> delete(@PathVariable("id") Long id) {
        omsService.del(id);
        return R.success();
    }
}
