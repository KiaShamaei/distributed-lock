package com.kia.distributedlock.service;

import com.kia.distributedlock.annotation.RedisLock;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class TestService {
    @RedisLock(key="test" ,timeout = 30L , timeUnit = TimeUnit.SECONDS)
    public String testLock(String key){
        return "test lock with annotation ... " + key;
    }
}
