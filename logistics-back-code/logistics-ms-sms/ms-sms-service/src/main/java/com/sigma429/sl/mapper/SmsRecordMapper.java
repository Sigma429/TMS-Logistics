package com.sigma429.sl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sigma429.sl.entity.SmsRecordEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 短信发送记录 Mapper 接口
 */
@Mapper
public interface SmsRecordMapper extends BaseMapper<SmsRecordEntity> {
}
