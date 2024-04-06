package com.sigma429.sl.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.itheima.em.sdk.EagleMapTemplate;
import com.itheima.em.sdk.enums.ProviderEnum;
import com.itheima.em.sdk.vo.Coordinate;
import com.itheima.em.sdk.vo.GeoResult;
import com.sigma429.sl.entity.ServiceScopeEntity;
import com.sigma429.sl.enums.ServiceTypeEnum;
import com.sigma429.sl.service.ScopeService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * ClassName:ScopeServiceImpl
 * Package:com.sigma429.sl.service.impl
 * Description:
 * @Author:14亿少女的梦-Sigma429
 * @Create:2024/04/06 - 20:23
 * @Version:v1.0
 */
@Slf4j
@Service
public class ScopeServiceImpl implements ScopeService {

    @Resource
    private MongoTemplate mongoTemplate;
    @Resource
    private EagleMapTemplate eagleMapTemplate;

    @Override
    public Boolean saveOrUpdate(Long bid, ServiceTypeEnum type, GeoJsonPolygon polygon) {
        // 构造查询条件
        Query query = Query.query(Criteria.where("bid").is(bid).and("type").is(type.getCode()));
        ServiceScopeEntity serviceScopeEntity = this.mongoTemplate.findOne(query, ServiceScopeEntity.class);
        if (ObjectUtil.isEmpty(serviceScopeEntity)) {
            // 新增
            serviceScopeEntity = new ServiceScopeEntity();
            serviceScopeEntity.setBid(bid);
            serviceScopeEntity.setType(type.getCode());
            serviceScopeEntity.setPolygon(polygon);
            serviceScopeEntity.setCreated(System.currentTimeMillis());
            serviceScopeEntity.setUpdated(serviceScopeEntity.getCreated());
        } else {
            // 更新
            serviceScopeEntity.setPolygon(polygon);
            serviceScopeEntity.setUpdated(System.currentTimeMillis());
        }

        try {
            this.mongoTemplate.save(serviceScopeEntity);
            return true;
        } catch (Exception e) {
            log.error("新增/更新服务范围数据失败！ bid = {}, type = {}, points = {}", bid, type, polygon.getPoints(), e);
        }
        return false;
    }

    @Override
    public Boolean delete(String id) {
        // 构造查询条件
        Query query = Query.query(Criteria.where("id").is(new ObjectId(id)));
        return this.mongoTemplate.remove(query, ServiceScopeEntity.class).getDeletedCount() > 0;
    }

    @Override
    public Boolean delete(Long bid, ServiceTypeEnum type) {
        // 构造查询条件
        Query query = Query.query(Criteria.where("bid").is(bid).and("type").is(type.getCode()));
        return this.mongoTemplate.remove(query, ServiceScopeEntity.class).getDeletedCount() > 0;
    }

    @Override
    public ServiceScopeEntity queryById(String id) {
        return this.mongoTemplate.findById(new ObjectId(id), ServiceScopeEntity.class);
    }

    @Override
    public ServiceScopeEntity queryByBidAndType(Long bid, ServiceTypeEnum type) {
        // 构造查询条件
        Query query = Query.query(Criteria.where("bid").is(bid).and("type").is(type.getCode()));
        return this.mongoTemplate.findOne(query, ServiceScopeEntity.class);
    }

    @Override
    public List<ServiceScopeEntity> queryListByPoint(ServiceTypeEnum type, GeoJsonPoint point) {
        Query query = Query.query(Criteria.where("polygon").intersects(point)
                .and("type").is(type.getCode()));
        return this.mongoTemplate.find(query, ServiceScopeEntity.class);
    }

    @Override
    public List<ServiceScopeEntity> queryListByPoint(ServiceTypeEnum type, String address) {
        // 根据详细地址查询坐标
        GeoResult geoResult = this.eagleMapTemplate.opsForBase().geoCode(ProviderEnum.AMAP, address, null);
        Coordinate coordinate = geoResult.getLocation();
        return this.queryListByPoint(type, new GeoJsonPoint(coordinate.getLongitude(), coordinate.getLatitude()));
    }
}