package com.sigma429.sl.controller;

import com.github.benmanes.caffeine.cache.Cache;
import com.sigma429.sl.dto.TransportInfoDTO;
import com.sigma429.sl.entity.TransportInfoEntity;
import com.sigma429.sl.enums.ExceptionEnum;
import com.sigma429.sl.exception.SLException;
import com.sigma429.sl.service.BloomFilterService;
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

    @Resource
    private Cache<String, TransportInfoDTO> transportInfoCache;

    @Resource
    private BloomFilterService bloomFilterService;

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
        // 如果布隆过滤器中不存在，无需缓存命中，直接返回即可
        boolean contains = this.bloomFilterService.contains(transportOrderId);
        if (!contains) {
            throw new SLException(ExceptionEnum.NOT_FOUND);
        }
        TransportInfoDTO transportInfoDTO = transportInfoCache.get(transportOrderId, id -> {
            // 未命中，查询MongoDB
            TransportInfoEntity transportInfoEntity = this.transportInfoService.queryByTransportOrderId(id);
            // 转化成DTO
            return BeanUtil.toBean(transportInfoEntity, TransportInfoDTO.class);
        });

        if (ObjectUtil.isNotEmpty(transportInfoDTO)) {
            return transportInfoDTO;
        }
        throw new SLException(ExceptionEnum.NOT_FOUND);
    }

}
