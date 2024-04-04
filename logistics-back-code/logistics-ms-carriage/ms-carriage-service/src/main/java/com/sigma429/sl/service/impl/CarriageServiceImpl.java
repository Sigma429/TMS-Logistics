package com.sigma429.sl.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sigma429.sl.common.AreaFeign;
import com.sigma429.sl.constant.CarriageConstant;
import com.sigma429.sl.dto.CarriageDTO;
import com.sigma429.sl.dto.WaybillDTO;
import com.sigma429.sl.entity.CarriageEntity;
import com.sigma429.sl.enums.CarriageExceptionEnum;
import com.sigma429.sl.exception.SLException;
import com.sigma429.sl.handler.CarriageChainHandler;
import com.sigma429.sl.mapper.CarriageMapper;
import com.sigma429.sl.service.CarriageService;
import com.sigma429.sl.util.ObjectUtil;
import com.sigma429.sl.utils.CarriageUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName:CarriageServiceImpl
 * Package:com.sigma429.sl.service.impl
 * Description:
 * @Author:14亿少女的梦-Sigma429
 * @Create:2024/03/26 - 21:43
 * @Version:v1.0
 */
@Service
public class CarriageServiceImpl extends ServiceImpl<CarriageMapper, CarriageEntity> implements CarriageService {
    @Resource
    private AreaFeign areaFeign;

    @Resource
    private CarriageChainHandler carriageChainHandler;

    @Override
    public CarriageDTO saveOrUpdate(CarriageDTO carriageDto) {
        // 校验运费模板是否存在，如果不存在直接插入（查询条件：模板类型  运输类型  如果是修改排除当前id）
        LambdaQueryWrapper<CarriageEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(CarriageEntity::getTemplateType, carriageDto.getTemplateType());
        queryWrapper.eq(CarriageEntity::getTransportType, carriageDto.getTransportType());
        queryWrapper.ne(ObjectUtils.isNotEmpty(carriageDto.getId()), CarriageEntity::getId, carriageDto.getId());

        List<CarriageEntity> carriageEntityList = super.list(queryWrapper);

        // 如果没有重复的模板，可以直接插入或更新操作（DTo转entity 保存成功 entity转DTO）
        if (CollUtil.isEmpty(carriageEntityList)) {
            return saveOrUpdateCarriage(carriageDto);
        }
        // 如果存在重复模板，需要判断此次插入的是否为经济区互寄，非经济区互寄不可以重复
        if (ObjectUtil.notEqual(carriageDto.getTemplateType(), CarriageConstant.ECONOMIC_ZONE)) {
            throw new SLException(CarriageExceptionEnum.NOT_ECONOMIC_ZONE_REPEAT);
        }
        // 如果是经济区互寄类型，需要进一步判断关联城市是否重复，通过集合取交集判断是否重复
        List<String> associatedCityList = carriageEntityList.stream().map(CarriageEntity::getAssociatedCity)
                .map(associatedCity -> StrUtil.splitToArray(associatedCity, ","))
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
        Collection<String> intersection = CollUtil.intersection(associatedCityList,
                carriageDto.getAssociatedCityList());
        if (CollUtil.isNotEmpty(intersection)) {
            throw new SLException(CarriageExceptionEnum.ECONOMIC_ZONE_CITY_REPEAT);
        }
        // 如果没有重复，可以新增或更新（DTO转Entity 保存成功 entity转DTO）
        return saveOrUpdateCarriage(carriageDto);
    }

    private CarriageDTO saveOrUpdateCarriage(CarriageDTO carriageDto) {
        CarriageEntity carriageEntity = CarriageUtils.toEntity(carriageDto);
        super.saveOrUpdate(carriageEntity);
        return CarriageUtils.toDTO(carriageEntity);
    }

    @Override
    public List<CarriageDTO> findAll() {
        // 构造查询条件，按创建时间倒叙
        LambdaQueryWrapper<CarriageEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByDesc(CarriageEntity::getCreated);

        // 查询数据库
        List<CarriageEntity> list = super.list(queryWrapper);

        // 将结果转换为DTO类型，使用CarriageUtils工具类
        return list.stream().map(CarriageUtils::toDTO).collect(Collectors.toList());
    }

    @Override
    public CarriageDTO compute(WaybillDTO waybillDTO) {
        // 根据参数查找运费模板
        CarriageEntity carriage = carriageChainHandler.findCarriage(waybillDTO);

        // 计算重量，确保最小重量为1kg
        double computeWeight = getComputeWeight(waybillDTO, carriage);

        // 计算运费  运费=首重价格 + （实际重量 - 1） * 续重加格
        double price = carriage.getFirstWeight() + (computeWeight - 1) * carriage.getContinuousWeight();

        // 结果保留一位小数
        BigDecimal expense = NumberUtil.round(price, 1);

        // 封装运费和计算重量到CarriageDTO，并返回
        CarriageDTO carriageDTO = CarriageUtils.toDTO(carriage);
        carriageDTO.setExpense(expense.doubleValue());
        carriageDTO.setComputeWeight(computeWeight);

        return carriageDTO;
    }

    /**
     * 根据体积参数与实际重量计算计费重量
     * @param waybillDTO 运费计算对象
     * @param carriage   运费模板
     * @return 计费重量
     */
    private double getComputeWeight(WaybillDTO waybillDTO, CarriageEntity carriage) {
        // 计算体积,如果传入体积则不需要计算
        Integer volume = waybillDTO.getVolume();
        if (ObjectUtil.isEmpty(volume)) {
            try {
                volume = waybillDTO.getMeasureHigh() * waybillDTO.getMeasureLong() * waybillDTO.getMeasureWidth();
            } catch (Exception e) {
                volume = 0;
            }
        }

        // 计算体积重量 = 体积 / 轻抛系数 tips:使用NumberUtil工具类计算 保留一位小数
        BigDecimal volumeWeight = NumberUtil.div(volume, carriage.getLightThrowingCoefficient(), 1);

        // 重量取最大值 = 体积重量和实际重量 tips:使用NumberUtil工具类计算 保留一位小数
        double computeWeight = NumberUtil.max(volumeWeight, NumberUtil.round(waybillDTO.getWeight(), 1)).doubleValue();

        // 计算续重,规则:不满1kg,按1kg计费
        if (computeWeight <= 1) {
            return 1;
        }

        // 10KG以下续重以0.1kg计量保留1位小数
        if (computeWeight <= 10) {
            return computeWeight;
        }

        // 100KG 以上四舍五入取整,举例108.4kg按照108收费  108.5kg 按照109KG收费
        // ips:使用NumberUtil工具类计算
        if (computeWeight >= 100) {
            return NumberUtil.round(computeWeight, 0).doubleValue();
        }

        // 0-100kg续重以0.5kg计量保留1位小数
        int intValue = NumberUtil.round(computeWeight, 0, RoundingMode.DOWN).intValue();

        // 0.5为一个计量单位,举例:18.8kg按照19收费,18.4kg按照18.5收费,18.1kg按照18.5kg收费
        double sub = NumberUtil.sub(computeWeight, intValue);
        if (sub == 0) {
            return intValue;
        }
        if (sub < 0.5) {
            return NumberUtil.add(intValue, 0.5);
        }
        return NumberUtil.add(intValue, 0.5);
    }

    @Override
    public CarriageEntity findByTemplateType(Integer templateType) {
        // 根据模板类型，及运输类型 = CarriageConst.REGULAR_FAST查询模板
        LambdaQueryWrapper<CarriageEntity> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(CarriageEntity::getTemplateType, templateType);
        queryWrapper.eq(CarriageEntity::getTransportType, CarriageConstant.REGULAR_FAST);
        return super.getOne(queryWrapper);
    }
}
