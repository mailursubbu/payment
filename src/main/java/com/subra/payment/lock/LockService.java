package com.subra.payment.lock;

import com.subra.payment.model.Account;

public interface LockService {
   void acquireLock(Object entity);
   void releaseLock(Object entity);
}
