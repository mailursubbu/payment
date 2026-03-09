package com.subra.payment.repository;

import com.subra.payment.lock.LockService;
import com.subra.payment.lock.LockServiceImpl;
import com.subra.payment.model.Account;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountRepoImplTest {

    LockService lockService = new LockServiceImpl();
    AccountRepo accountRepo = new AccountRepoImpl(lockService);
    @Test
    void save() {
        Account acc =  Account.builder()
                .documentId("documentId")
                .build();
        var accResult = accountRepo.save(acc);
        assertNotNull(accResult);
        assertEquals(1, accResult.getId());
        assertEquals("documentId", accResult.getDocumentId());
    }

    @Test
    void getById() {
        Account acc =  Account.builder()
                .documentId("documentId")
                .build();
        accountRepo.save(acc);
        var accResult = accountRepo.getById(1).get();
        assertNotNull(accResult);
        assertEquals(1, accResult.getId());
        assertEquals("documentId", accResult.getDocumentId());

    }
}