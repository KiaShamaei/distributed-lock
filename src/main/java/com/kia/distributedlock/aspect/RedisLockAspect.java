package com.kia.distributedlock.aspect;

import com.kia.distributedlock.annotation.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
@Component
@Aspect
@Slf4j
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
                log.info("Lock acquired. Operation started.");
            } else {
                log.error("Failed to acquire lock. Resource is busy.");
            }
            return joinPoint.proceed();
        } finally {
            if (acquired) {
                redisTemplate.delete(lockKey);
            }
        }
    }
}
