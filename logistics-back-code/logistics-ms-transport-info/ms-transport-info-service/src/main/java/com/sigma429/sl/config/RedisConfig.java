package com.sigma429.sl.config;

/**
 * ClassName:RedisConfig
 * Package:com.sigma429.sl.config
 * Description:
 * @Author:14亿少女的梦-Sigma429
 * @Create:2024/04/07 - 22:07
 * @Version:v1.0
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * Redis相关的配置
 */
@Configuration
public class RedisConfig {

    /**
     * 存储的默认有效期时间，单位：小时
     */
    @Value("${redis.ttl:1}")
    private Integer redisTtl;

    @Bean
    public RedisCacheManager redisCacheManager(RedisTemplate redisTemplate) {
        // 默认配置
        RedisCacheConfiguration defaultCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                // 设置key的序列化方式为字符串
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                // 设置value的序列化方式为json格式
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                // 不缓存null
                .disableCachingNullValues()
                // 默认缓存数据保存1小时
                .entryTtl(Duration.ofHours(redisTtl));

        // 构redis缓存管理器
        RedisCacheManager redisCacheManager = RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisTemplate.getConnectionFactory())
                .cacheDefaults(defaultCacheConfiguration)
                // 只在事务成功提交后才会进行缓存的put/evict操作
                .transactionAware()
                .build();
        return redisCacheManager;
    }
}
