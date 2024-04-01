package com.sigma429.sl.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sigma429.sl.entity.TransportOrderTaskEntity;
import com.sigma429.sl.mapper.TransportOrderTaskMapper;
import com.sigma429.sl.service.TransportOrderTaskService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 运单表 服务实现类
 */
@Service
public class TransportOrderTaskServiceImpl extends
        ServiceImpl<TransportOrderTaskMapper, TransportOrderTaskEntity> implements TransportOrderTaskService {

    @Override
    public void batchSaveTransportOrder(List<TransportOrderTaskEntity> transportOrderTaskList) {

    }

    @Override
    public IPage<TransportOrderTaskEntity> findByPage(Integer page, Integer pageSize, String transportOrderId,
                                                      Long transportTaskId) {
        return null;
    }

    @Override
    public List<TransportOrderTaskEntity> findAll(String transportOrderId, Long transportTaskId) {
        return null;
    }

    @Override
    public Long count(String transportOrderId, Long transportTaskId) {
        return null;
    }

    @Override
    public void del(String transportOrderId, Long transportTaskId) {

    }
}
