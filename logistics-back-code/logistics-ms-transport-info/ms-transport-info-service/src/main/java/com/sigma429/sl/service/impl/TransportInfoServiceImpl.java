package com.sigma429.sl.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.sigma429.sl.dto.TransportInfoDTO;
import com.sigma429.sl.entity.TransportInfoDetail;
import com.sigma429.sl.entity.TransportInfoEntity;
import com.sigma429.sl.service.TransportInfoService;
import com.sigma429.sl.util.ObjectUtil;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * ClassName:TransportInfoServiceImpl
 * Package:com.sigma429.sl.service.impl
 * Description:
 * @Author:14亿少女的梦-Sigma429
 * @Create:2024/04/07 - 17:46
 * @Version:v1.0
 */
@Service
public class TransportInfoServiceImpl implements TransportInfoService {
    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private Cache<String, TransportInfoDTO> transportInfoCache;

    @Override
    // 与@Cacheable不同的是使用@CachePut标注的方法在执行前不会去检查缓存中是否存在之前执行过的结果，而是每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中。
    @CachePut(value = "transport-info", key = "#p0") // 更新缓存数据
    public TransportInfoEntity saveOrUpdate(String transportOrderId, TransportInfoDetail infoDetail) {
        // 根据运单id查询
        // 构造查询条件
        Query query = Query.query(Criteria.where("transportOrderId").is(transportOrderId));
        TransportInfoEntity transportInfoEntity = this.mongoTemplate.findOne(query, TransportInfoEntity.class);
        if (ObjectUtil.isEmpty(transportInfoEntity)) {
            // 运单信息不存在，新增数据
            transportInfoEntity = new TransportInfoEntity();
            transportInfoEntity.setTransportOrderId(transportOrderId);
            transportInfoEntity.setInfoList(ListUtil.toList(infoDetail));
            transportInfoEntity.setCreated(System.currentTimeMillis());
        } else {
            // 运单信息存在，只需要追加物流详情数据
            transportInfoEntity.getInfoList().add(infoDetail);
        }
        // 无论新增还是更新都要设置更新时间
        transportInfoEntity.setUpdated(System.currentTimeMillis());

        // 清除缓存中的数据
        // 下次查询时会从二级缓存中查询到数据，再存储到Caffeine中。
        this.transportInfoCache.invalidate(transportOrderId);

        // 保存/更新到MongoDB
        return this.mongoTemplate.save(transportInfoEntity);
    }

    @Override
    // 在每次执行前都会检查Cache中是否存在相同key的缓存元素，如果存在就不再执行该方法，而是直接从缓存中获取结果进行返回，否则才会执行并将返回结果存入指定的缓存中。
    @Cacheable(value = "transport-info", key = "#p0") // 新增缓存数据
    public TransportInfoEntity queryByTransportOrderId(String transportOrderId) {
        // 定义查询条件
        Query query = Query.query(Criteria.where("transportOrderId").is(transportOrderId));
        // 查询数据
        return this.mongoTemplate.findOne(query, TransportInfoEntity.class);
    }
}
