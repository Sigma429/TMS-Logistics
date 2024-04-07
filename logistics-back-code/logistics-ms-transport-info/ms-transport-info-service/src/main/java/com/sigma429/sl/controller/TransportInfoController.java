package com.sigma429.sl.controller;

import com.sigma429.sl.dto.TransportInfoDTO;
import com.sigma429.sl.entity.TransportInfoEntity;
import com.sigma429.sl.enums.ExceptionEnum;
import com.sigma429.sl.exception.SLException;
import com.sigma429.sl.service.TransportInfoService;
import com.sigma429.sl.util.BeanUtil;
import com.sigma429.sl.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "物流信息")
@RestController
@RequestMapping("infos")
public class TransportInfoController {

    @Resource
    private TransportInfoService transportInfoService;

    /**
     * 根据运单id查询运单信息
     * @param transportOrderId 运单号
     * @return 运单信息
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "transportOrderId", value = "运单id")
    })
    @ApiOperation(value = "查询", notes = "根据运单id查询物流信息")
    @GetMapping("{transportOrderId}")
    public TransportInfoDTO queryByTransportOrderId(@PathVariable("transportOrderId") String transportOrderId) {
        TransportInfoEntity transportInfoEntity = transportInfoService.queryByTransportOrderId(transportOrderId);
        if (ObjectUtil.isNotEmpty(transportInfoEntity)) {
            return BeanUtil.toBean(transportInfoEntity, TransportInfoDTO.class);
        }
        throw new SLException(ExceptionEnum.NOT_FOUND);
    }

}
