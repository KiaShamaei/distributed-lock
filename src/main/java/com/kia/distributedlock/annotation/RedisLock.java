package com.kia.distributedlock.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
public @interface RedisLock {
    String key();
    long timeout() default 30;
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}
