package com.kia.distributedlock.controller;

import com.kia.distributedlock.annotation.RedisLock;
import com.kia.distributedlock.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("api/v2/")
@Slf4j
public class TestControllerWithAnnotation {

    private final TestService service;

    public TestControllerWithAnnotation(TestService service) {
        this.service = service;
    }


    @GetMapping("/perform/{lockKey}")
    public String performOperation(@PathVariable String lockKey) throws InterruptedException {
        var res =  service.testLock(lockKey);
        return res;
    }
}
