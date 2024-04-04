package com.sigma429.sl.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.sigma429.sl.domain.RefundRecordDTO;
import com.sigma429.sl.entity.RefundRecordEntity;
import com.sigma429.sl.enums.TradingEnum;
import com.sigma429.sl.exception.SLException;
import com.sigma429.sl.service.RefundRecordService;
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

@RestController
@Api(tags = "退款单服务")
@RequestMapping("refund/records")
public class RefundRecordController {

    @Resource
    private RefundRecordService refundRecordService;

    /**
     * 根据业务系统订单号 或 交易单号查询退款单 （二个至少传递一个，优先按照交易单号查询）
     * @param productOrderNo 业务订单号
     * @param tradingOrderNo 交易单号
     * @return 退款单列表
     */
    @GetMapping
    @ApiOperation(value = "查询退款单", notes = "根据业务系统订单号 或 交易单号查询退款单 （二个至少传递一个，优先按照交易单号查询）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productOrderNo", value = "业务订单号"),
            @ApiImplicitParam(name = "tradingOrderNo", value = "交易单号")
    })
    public List<RefundRecordDTO> findList(@RequestParam(value = "productOrderNo", required = false) Long productOrderNo,
                                          @RequestParam(value = "tradingOrderNo", required = false) Long tradingOrderNo) {
        if (ObjectUtil.isAllEmpty(productOrderNo, tradingOrderNo)) {
            throw new SLException(TradingEnum.REFUND_QUERY_PARAM_ERROR);
        }
        List<RefundRecordEntity> list;
        if (ObjectUtil.isNotEmpty(tradingOrderNo)) {
            list = refundRecordService.findListByTradingOrderNo(tradingOrderNo);
        } else {
            list = refundRecordService.findListByProductOrderNo(productOrderNo);
        }
        if (CollUtil.isEmpty(list)) {
            throw new SLException(TradingEnum.REFUND_NOT_FOUND);
        }
        return BeanUtil.copyToList(list, RefundRecordDTO.class);
    }

}
