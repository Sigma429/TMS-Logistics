package com.sigma429.sl;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:SimpleRedisLock
 * Package:com.sigma429.sl.handler
 * Description:
 * @Author:14亿少女的梦-Sigma429
 * @Create:2024/03/24 - 15:45
 * @Version:v1.0
 */
@Component
public class SimpleRedisLock implements ILock {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final String KEY_PREFIX = "lock:";

    @Override
    public boolean tryLock(String name, Long timeoutSec) {
        // 获取线程标示
        String threadId = Thread.currentThread().getId() + "";
        // 获取锁 setIfAbsent()是SETNX命令在java中的体现
        Boolean success = stringRedisTemplate.opsForValue()
                .setIfAbsent(KEY_PREFIX + name, threadId, timeoutSec, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(success);
    }

    @Override
    public void unlock(String name) {
        // 通过del删除锁
        stringRedisTemplate.delete(KEY_PREFIX + name);
    }
}
