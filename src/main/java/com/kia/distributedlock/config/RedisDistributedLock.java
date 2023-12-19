package com.kia.distributedlock.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisDistributedLock {
    private final RedisTemplate<String, Object> redisTemplate;
    public RedisDistributedLock(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    public boolean acquireLock(String lockKey, long timeout, TimeUnit unit) {
        log.info("redis lock run...component");
        return redisTemplate.opsForValue().setIfAbsent(lockKey, "locked", timeout, unit);
    }
    public void releaseLock(String lockKey) {
        redisTemplate.delete(lockKey);
    }
}
