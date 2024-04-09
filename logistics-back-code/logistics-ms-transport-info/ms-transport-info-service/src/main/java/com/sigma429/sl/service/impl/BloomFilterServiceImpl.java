package com.sigma429.sl.service.impl;

/**
 * ClassName:BloomFilterServiceImpl
 * Package:com.sigma429.sl.service.impl
 * Description:
 * @Author:14亿少女的梦-Sigma429
 * @Create:2024/04/08 - 19:27
 * @Version:v1.0
 */

import com.sigma429.sl.config.BloomFilterConfig;
import com.sigma429.sl.service.BloomFilterService;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service
public class BloomFilterServiceImpl implements BloomFilterService {

    @Resource
    private RedissonClient redissonClient;
    @Resource
    private BloomFilterConfig bloomFilterConfig;

    private RBloomFilter<Object> getBloomFilter() {
        return this.redissonClient.getBloomFilter(this.bloomFilterConfig.getName());
    }

    @Override
    @PostConstruct // spring启动后进行初始化
    public void init() {
        RBloomFilter<Object> bloomFilter = this.getBloomFilter();
        bloomFilter.tryInit(this.bloomFilterConfig.getExpectedInsertions(),
                this.bloomFilterConfig.getFalseProbability());
    }

    @Override
    public boolean add(Object obj) {
        return this.getBloomFilter().add(obj);
    }

    @Override
    public boolean contains(Object obj) {
        return this.getBloomFilter().contains(obj);
    }
}
