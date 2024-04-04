package com.sigma429.sl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sigma429.sl.entity.FailMsgEntity;
import com.sigma429.sl.mapper.FailMsgMapper;
import com.sigma429.sl.service.FailMsgService;
import com.sigma429.sl.service.MQService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

/**
 * 失败消息处理服务
 */
@Service
@ConditionalOnBean(MQService.class)
public class FailMsgServiceImpl extends ServiceImpl<FailMsgMapper, FailMsgEntity>
        implements FailMsgService {
}
