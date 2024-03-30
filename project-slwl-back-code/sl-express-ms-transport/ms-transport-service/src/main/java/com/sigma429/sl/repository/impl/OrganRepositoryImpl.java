package com.sigma429.sl.repository.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import com.sigma429.sl.domain.OrganDTO;
import com.sigma429.sl.enums.OrganTypeEnum;
import com.sigma429.sl.repository.OrganRepository;
import com.sigma429.sl.util.BeanUtil;
import com.sigma429.sl.util.ObjectUtil;
import org.neo4j.driver.internal.InternalPoint2D;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * ClassName:OrganRepositoryImpl
 * Package:com.sigma429.sl.repository.impl
 * Description:
 * @Author:14亿少女的梦-Sigma429
 * @Create:2024/03/30 - 22:31
 * @Version:v1.0
 */
@Component
public class OrganRepositoryImpl implements OrganRepository {

    @Resource
    private Neo4jClient neo4jClient;

    @Override
    public OrganDTO findByBid(Long bid) {
        String cypherQuery = StrUtil.format("MATCH (n)\n" +
                "WHERE n.bid = {}\n" +
                "RETURN n", bid);
        return CollUtil.getFirst(executeQuery(cypherQuery));
    }

    @Override
    public List<OrganDTO> findByBids(List<Long> bids) {
        String cypherQuery = StrUtil.format("MATCH (n)\n" +
                "WHERE n.bid in {}\n" +
                "RETURN n", bids);
        return executeQuery(cypherQuery);
    }

    @Override
    public List<OrganDTO> findAll(String name) {
        name = StrUtil.removeAll(name, '\'', '"');
        String cypherQuery = StrUtil.isEmpty(name) ?
                "MATCH (n) RETURN n" :
                StrUtil.format("MATCH (n) WHERE n.name CONTAINS '{}' RETURN n", name);
        return executeQuery(cypherQuery);
    }

    private List<OrganDTO> executeQuery(String cypherQuery) {
        return ListUtil.toList(neo4jClient.query(cypherQuery)
                // 设置响应的类型
                .fetchAs(OrganDTO.class)
                // 对结果进行封装处理
                .mappedBy((typeSystem, record) -> {
                    Map<String, Object> map = record.get("n").asMap();
                    OrganDTO organDTO = BeanUtil.toBean(map, OrganDTO.class);
                    InternalPoint2D location = (InternalPoint2D) map.get("location");
                    if (ObjectUtil.isNotEmpty(location)) {
                        organDTO.setLongitude(location.x());
                        organDTO.setLatitude(location.y());
                    }
                    // 获取类型
                    String type = CollUtil.getFirst(record.get("n").asNode().labels());
                    organDTO.setType(OrganTypeEnum.valueOf(type).getCode());
                    return organDTO;
                }).all());
    }
}