package com.sigma429.sl.handler;

import com.sigma429.sl.common.AreaFeign;
import com.sigma429.sl.constant.CarriageConstant;
import com.sigma429.sl.dto.WaybillDTO;
import com.sigma429.sl.entity.CarriageEntity;
import com.sigma429.sl.service.CarriageService;
import com.sigma429.sl.util.ObjectUtil;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 省内寄
 */
@Order(200) // 定义顺序
@Component
public class SameProvinceChainHandler extends AbstractCarriageChainHandler {

    @Resource
    private CarriageService carriageService;
    @Resource
    private AreaFeign areaFeign;

    @Override
    public CarriageEntity doHandler(WaybillDTO waybillDTO) {
        CarriageEntity carriageEntity = null;
        // 获取收寄件地址省份id
        Long receiverProvinceId = areaFeign.get(waybillDTO.getReceiverCityId()).getParentId();
        Long senderProvinceId = areaFeign.get(waybillDTO.getSenderCityId()).getParentId();
        if (ObjectUtil.equal(receiverProvinceId, senderProvinceId)) {
            // 省内
            carriageEntity = carriageService.findByTemplateType(CarriageConstant.SAME_PROVINCE);
        }
        return doNextHandler(waybillDTO, carriageEntity);
    }
}