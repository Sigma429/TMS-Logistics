package com.sigma429.sl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.junit.jupiter.api.Test;

/**
 * ClassName:CaffeineTest
 * Package:com.sigma429.sl
 * Description:
 * @Author:14亿少女的梦-Sigma429
 * @Create:2024/04/07 - 21:40
 * @Version:v1.0
 */
public class CaffeineTest {
    @Test
    public void testCaffeine() {
        // 创建缓存对象
        Cache<String, Object> cache = Caffeine.newBuilder()
                .initialCapacity(10) // 缓存初始容量
                .maximumSize(100) // 缓存最大容量
                .build();

       cache.cleanUp();

        Object value2 = cache.get("key2", key -> 456);
        System.out.println(value2); // 456
    }
}
