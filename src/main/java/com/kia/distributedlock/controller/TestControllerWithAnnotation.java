package com.kia.distributedlock.controller;

import com.kia.distributedlock.annotation.RedisLock;
import com.kia.distributedlock.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("api/v2/")
public class TestControllerWithAnnotation {
//    private final LockService lockService;
//    public TestControllerWithAnnotation(LockService lockService) {
//        this.lockService = lockService;
//    }

    private final TestService service;

    public TestControllerWithAnnotation(TestService service) {
        this.service = service;
    }


    @GetMapping("/perform/{lockKey}")
    public String performOperation(@PathVariable String lockKey) throws InterruptedException {

        return service.testLock(lockKey);
    }
}
