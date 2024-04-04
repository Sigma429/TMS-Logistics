package com.sigma429.sl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sigma429.sl.entity.FailMsgEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 失败消息记录mapper
 */
@Mapper
public interface FailMsgMapper extends BaseMapper<FailMsgEntity> {
}
