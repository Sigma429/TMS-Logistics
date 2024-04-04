package com.sigma429.sl.service.impl;

import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sigma429.sl.constant.Constant;
import com.sigma429.sl.entity.TradingEntity;
import com.sigma429.sl.enums.TradingStateEnum;
import com.sigma429.sl.mapper.TradingMapper;
import com.sigma429.sl.service.TradingService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description：交易订单表 服务实现类
 */
@Service
public class TradingServiceImpl extends ServiceImpl<TradingMapper, TradingEntity> implements TradingService {

    @Override
    public TradingEntity findTradByTradingOrderNo(Long tradingOrderNo) {
        LambdaQueryWrapper<TradingEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TradingEntity::getTradingOrderNo, tradingOrderNo);
        return super.getOne(queryWrapper);
    }

    @Override
    public TradingEntity findTradByProductOrderNo(Long productOrderNo) {
        LambdaQueryWrapper<TradingEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TradingEntity::getProductOrderNo, productOrderNo);
        return super.getOne(queryWrapper);
    }

    @Override
    public List<TradingEntity> findListByTradingState(TradingStateEnum tradingState, Integer count) {
        count = NumberUtil.max(count, 10);
        LambdaQueryWrapper<TradingEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TradingEntity::getTradingState, tradingState)
                .eq(TradingEntity::getEnableFlag, Constant.YES)
                .orderByAsc(TradingEntity::getCreated)
                .last("LIMIT " + count);
        return list(queryWrapper);
    }
}
