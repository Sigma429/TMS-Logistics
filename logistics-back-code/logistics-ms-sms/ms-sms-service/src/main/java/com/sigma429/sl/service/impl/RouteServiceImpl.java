package com.sigma429.sl.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sigma429.sl.entity.SmsThirdChannelEntity;
import com.sigma429.sl.enums.SmsContentTypeEnum;
import com.sigma429.sl.enums.SmsTypeEnum;
import com.sigma429.sl.mapper.SmsThirdChannelMapper;
import com.sigma429.sl.service.RouteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteServiceImpl extends ServiceImpl<SmsThirdChannelMapper, SmsThirdChannelEntity> implements RouteService {

    /**
     * 目前只根据优先级进行路由选出前五，然后随机选择渠道
     * @param smsTypeEnum        短信类型
     * @param smsContentTypeEnum 内容类型
     * @param smsCode            短信code，短信微服务发放的code，与sms_code是一对多的关系
     * @return 选中的发送通道信息
     */
    @Override
    public SmsThirdChannelEntity route(SmsTypeEnum smsTypeEnum, SmsContentTypeEnum smsContentTypeEnum, String smsCode) {

        // 取前五条记录
        LambdaQueryWrapper<SmsThirdChannelEntity> queryWrapper = Wrappers.<SmsThirdChannelEntity>lambdaQuery()
                .eq(SmsThirdChannelEntity::getSmsType, smsTypeEnum)
                .eq(SmsThirdChannelEntity::getContentType, smsContentTypeEnum)
                .eq(SmsThirdChannelEntity::getSmsCode, smsCode)
                // 使用中的状态
                .eq(SmsThirdChannelEntity::getStatus, 1)
                .orderByDesc(SmsThirdChannelEntity::getSmsPriority).last("LIMIT 5");

        // 查询数据
        List<SmsThirdChannelEntity> smsThirdChannelEntities = super.list(queryWrapper);
        if (CollUtil.isEmpty(smsThirdChannelEntities)) {
            return null;
        }

        // 随机选择
        int index = RandomUtil.randomInt(0, CollUtil.size(smsThirdChannelEntities));
        return CollUtil.get(smsThirdChannelEntities, index);
    }
}
