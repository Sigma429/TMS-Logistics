package com.sigma429.sl.service.impl;

import cn.hutool.core.bean.copier.CopyOptions;
import com.sigma429.sl.entity.node.BaseEntity;
import com.sigma429.sl.repository.BaseRepository;
import com.sigma429.sl.service.IService;
import com.sigma429.sl.util.BeanUtil;
import com.sigma429.sl.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/*
 *基础服务的实现
 */
public class ServiceImpl<R extends BaseRepository, T extends BaseEntity> implements IService<T> {

    @Autowired
    private R repository;

    @Override
    public T queryByBid(Long bid) {
        return (T) this.repository.findByBid(bid).orElse(null);
    }

    @Override
    public T create(T t) {
        // id由neo4j自动生成
        t.setId(null);
        return (T) this.repository.save(t);
    }

    @Override
    public T update(T t) {
        // 先查询，再更新
        T tData = this.queryByBid(t.getBid());
        if (ObjectUtil.isEmpty(tData)) {
            return null;
        }
        BeanUtil.copyProperties(t, tData, CopyOptions.create().ignoreNullValue().setIgnoreProperties("id", "bid"));
        return (T) this.repository.save(tData);
    }

    @Override
    public Boolean deleteByBid(Long bid) {
        return this.repository.deleteByBid(bid) > 0;
    }
}
