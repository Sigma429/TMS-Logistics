package com.sigma429.sl;

/**
 * ClassName:ILock
 * Package:com.sigma429.sl.handler
 * Description:
 * @Author:14亿少女的梦-Sigma429
 * @Create:2024/03/24 - 15:44
 * @Version:v1.0
 */
public interface ILock {
    /**
     * 尝试获取锁
     * @param name       锁的名称
     * @param timeoutSec 锁持有的超时时间，过期后自动释放
     * @return true表示获取锁成功，false表示获取锁失败
     */
    boolean tryLock(String name, Long timeoutSec);

    /**
     * 释放锁
     */
    void unlock(String name);
}
