package com.sigma429.sl.service.truck.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sigma429.sl.entity.truck.TruckEntity;
import com.sigma429.sl.entity.truck.TruckLicenseEntity;
import com.sigma429.sl.mapper.truck.TruckLicenseMapper;
import com.sigma429.sl.service.truck.TruckLicenseService;
import com.sigma429.sl.service.truck.TruckService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 车辆行驶证表  服务类
 */
@Service
public class TruckLicenseServiceImpl extends ServiceImpl<TruckLicenseMapper, TruckLicenseEntity> implements TruckLicenseService {

    @Resource
    private TruckService truckService;

    /**
     * 保存车辆行驶证信息
     * @param truckLicenseEntity 车辆行驶证信息
     * @return 车辆行驶证信息
     */
    @Transactional
    @Override
    public TruckLicenseEntity saveTruckLicense(TruckLicenseEntity truckLicenseEntity) {
        if (truckLicenseEntity.getId() == null) {
            super.save(truckLicenseEntity);
            // 处理车辆信息中的关联字段
            if (ObjectUtil.isNotEmpty(truckLicenseEntity.getTruckId())) {
                TruckEntity truckEntity = truckService.getById(truckLicenseEntity.getTruckId());
                truckEntity.setTruckLicenseId(truckLicenseEntity.getId());
                truckService.updateById(truckEntity);
            }
        } else {
            super.updateById(truckLicenseEntity);
        }
        return truckLicenseEntity;
    }
}
