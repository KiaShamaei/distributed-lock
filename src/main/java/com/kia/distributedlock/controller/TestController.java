package com.kia.distributedlock.controller;

import com.kia.distributedlock.service.LockService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private final LockService lockService;
    public TestController(LockService lockService) {
        this.lockService = lockService;
    }



    @GetMapping("/perform/{lockKey}")
    public String performOperation(@PathVariable String lockKey) throws InterruptedException {
        lockService.performWithLock(lockKey);
        return "Operation completed";
    }
}
