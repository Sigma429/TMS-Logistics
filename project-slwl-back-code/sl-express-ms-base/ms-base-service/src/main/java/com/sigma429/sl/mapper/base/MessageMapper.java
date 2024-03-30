package com.sigma429.sl.mapper.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sigma429.sl.entity.base.MessageEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 消息表 mapper接口
 */
@Mapper
public interface MessageMapper extends BaseMapper<MessageEntity> {

}
