package com.subra.payment.lock;

import com.subra.payment.model.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
@Slf4j
public class LockServiceImpl implements LockService {

    Map<Object, Lock> locks = new ConcurrentHashMap<>();
    @Override
    public void acquireLock(Object entity) {
        log.info("Lock requested for entity: {} at {} for thread id : {}", entity, System.currentTimeMillis(), Thread.currentThread().getId());
        Lock lock = locks.computeIfAbsent(entity, k -> new ReentrantLock());
        lock.lock();
        locks.put(entity, lock);
        log.info("Lock acquired for entity: {} at {} for thread id : {}", entity, System.currentTimeMillis(), Thread.currentThread().getId());
    }

    @Override
    public void releaseLock(Object entity) {
        log.info("Lock release requested for entity: {} at {} for thread id : {}", entity, System.currentTimeMillis(), Thread.currentThread().getId());
        Lock lock = locks.get(entity);
        lock.unlock();
        log.info("Lock release acquired for entity: {} at {} for thread id : {}", entity, System.currentTimeMillis(), Thread.currentThread().getId());
    }
}
