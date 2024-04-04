package com.sigma429.sl.controller;

import com.sigma429.sl.service.PayService;
import com.sigma429.sl.vo.R;
import com.sigma429.sl.vo.pay.TradeLaunchVO;
import com.sigma429.sl.vo.pay.TradeResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "支付相关接口")
@RestController
@RequestMapping("/pays")
public class PayController {
    @Resource
    private PayService payService;

    @ApiOperation("获取支付qrcode")
    @PostMapping("/qrCode/get")
    public R<TradeResponseVO> getQrCode(@RequestBody TradeLaunchVO tradeLaunchVO) {
        return R.success(payService.getQrCode(tradeLaunchVO));
    }

    @ApiOperation("获取支付状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productOrderNo", value = "订单号", required = true)
    })
    @GetMapping("/status/{productOrderNo}")
    public R<Boolean> status(@PathVariable("productOrderNo") String productOrderNo) {
        return R.success(payService.getStatus(productOrderNo));
    }

}
