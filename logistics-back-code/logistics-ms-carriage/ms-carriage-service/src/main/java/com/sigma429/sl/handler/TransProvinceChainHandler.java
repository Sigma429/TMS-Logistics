package com.sigma429.sl.handler;

import com.sigma429.sl.constant.CarriageConstant;
import com.sigma429.sl.dto.WaybillDTO;
import com.sigma429.sl.entity.CarriageEntity;
import com.sigma429.sl.service.CarriageService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 跨省
 */
@Order(400) // 定义顺序
@Component
public class TransProvinceChainHandler extends AbstractCarriageChainHandler {

    @Resource
    private CarriageService carriageService;

    @Override
    public CarriageEntity doHandler(WaybillDTO waybillDTO) {
        CarriageEntity carriageEntity = carriageService.findByTemplateType(CarriageConstant.TRANS_PROVINCE);
        return doNextHandler(waybillDTO, carriageEntity);
    }
}

