package com.kia.distributedlock.aspect;

import com.kia.distributedlock.annotation.RedisLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisLockAspect {
    private final RedisTemplate<String, String> redisTemplate;

    public RedisLockAspect(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Pointcut("@annotation(redisLock)")
    public void redisLockPointcut(RedisLock redisLock) {
    }

    @Around("redisLockPointcut(redisLock)")
    public Object applyLock(ProceedingJoinPoint joinPoint, RedisLock redisLock) throws Throwable {
        String lockKey = redisLock.key();
        long lockTimeout = redisLock.timeout();
        TimeUnit timeUnit = redisLock.timeUnit();

        boolean acquired = false;
        try {
            acquired = redisTemplate.opsForValue().setIfAbsent(lockKey, "locked", lockTimeout, timeUnit);
            if (acquired) {
                return joinPoint.proceed();
            } else {
                throw new RuntimeException("Failed to acquire lock for method: " + joinPoint.getSignature().toShortString());
            }
        } finally {
            if (acquired) {
                redisTemplate.delete(lockKey);
            }
        }
    }
}
