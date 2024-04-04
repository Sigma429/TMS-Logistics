package com.sigma429.sl.service.user.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sigma429.sl.entity.user.TruckDriverLicenseEntity;
import com.sigma429.sl.mapper.user.TruckDriverLicenseMapper;
import com.sigma429.sl.service.user.TruckDriverLicenseService;
import org.springframework.stereotype.Service;

/**
 * 司机驾驶证
 */
@Service
public class TruckDriverLicenseServiceImpl extends ServiceImpl<TruckDriverLicenseMapper, TruckDriverLicenseEntity>
        implements TruckDriverLicenseService {

    /**
     * 获取司机驾驶证信息
     * @param userId 司机id
     * @return 司机驾驶证信息
     */
    @Override
    public TruckDriverLicenseEntity findOne(Long userId) {
        LambdaQueryWrapper<TruckDriverLicenseEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (ObjectUtil.isNotEmpty(userId)) {
            lambdaQueryWrapper.eq(TruckDriverLicenseEntity::getUserId, userId);
        }
        return super.getOne(lambdaQueryWrapper);
    }
}
