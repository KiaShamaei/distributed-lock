package com.kia.distributedlock.service;

import com.kia.distributedlock.annotation.RedisLock;
import org.springframework.stereotype.Service;

@Service
public class TestService {

//    @RedisLock()
    public String testLock(String lockKey){
        return "test this key " + lockKey;
    }
}
