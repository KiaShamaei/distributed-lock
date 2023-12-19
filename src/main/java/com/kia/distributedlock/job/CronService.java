package com.kia.distributedlock.job;

import com.kia.distributedlock.config.RedisDistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class CronService {

    private final RedisDistributedLock lock;



    public CronService(RedisDistributedLock lock) {
        this.lock = lock;
    }

    @Scheduled(initialDelay = 12000,fixedDelay = 15000)
    private void cronMethod() throws InterruptedException {
        log.info("Cron job running..");
        if (lock.acquireLock("test", 15000, TimeUnit.MILLISECONDS)) {
            log.info("Lock acquired. Operation started.");

            Thread.sleep(200);

            log.info("Operation completed.");
        } else {
            log.error("Failed to acquire lock. Resource is busy.");
        }

    }
}
