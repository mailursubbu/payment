package com.subra.payment.service;

import com.subra.payment.dto.AccountDto;
import com.subra.payment.error.ApplicationException;
import com.subra.payment.lock.LockService;
import com.subra.payment.lock.LockServiceImpl;
import com.subra.payment.mapper.AccountMapperImpl;
import com.subra.payment.model.Account;
import com.subra.payment.repository.AccountRepo;
import com.subra.payment.repository.AccountRepoImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceImplTest {


    LockService LockService = new LockServiceImpl();

    AccountRepo accountRepo = new AccountRepoImpl(LockService);
    AccountMapperImpl accountMapper = new AccountMapperImpl();

    @Test
    void save() {
        AccountDto accountDto = AccountDto.builder()
                .document_number("documentId")
                .build();

        AccountServiceImpl accountService = new AccountServiceImpl(accountRepo, accountMapper);
        accountDto = accountService.save(accountDto);

        assertEquals("documentId", accountDto.getDocument_number());
        assertNotNull(accountDto.getAccount_id());

        int id = accountDto.getAccount_id();

        //Update the same and make sure that new id is not created and same record is updated
        accountDto.setDocument_number("newDocumentId");
        accountDto = accountService.save(accountDto);

        assertEquals("newDocumentId", accountDto.getDocument_number());
        assertEquals(id, accountDto.getAccount_id());
    }

    @Test
    void updateAccountBalanceTest() {
        AccountRepoImpl accountRepo = new AccountRepoImpl(LockService);

        Account account = Account.builder().id(1).documentId("test").accountLimit(-100.0).balance(0.0).build();
        accountRepo.save(account);
        AccountServiceImpl accountService = new AccountServiceImpl(accountRepo, accountMapper);
        AccountDto accountDto =  accountService.updateAccountBalance(1, -100.0);
        assertEquals(-100, accountDto.getBalance());

    }

    @Test
    void updateAccountBalanceTestFail() {
        AccountRepoImpl accountRepo = new AccountRepoImpl(LockService);

        Account account = Account.builder().id(1).documentId("test").accountLimit(-100.0).balance(0.0).build();
        accountRepo.save(account);
        AccountServiceImpl accountService = new AccountServiceImpl(accountRepo, accountMapper);
        assertThrows(ApplicationException.class, () -> accountService.updateAccountBalance(1, -101.0));
    }

    @Test
    void updateAccountBalanceTestPositive() {
        AccountRepoImpl accountRepo = new AccountRepoImpl(LockService);

        Account account = Account.builder().id(1).documentId("test").accountLimit(-100.0).balance(0.0).build();
        accountRepo.save(account);
        AccountServiceImpl accountService = new AccountServiceImpl(accountRepo, accountMapper);
        AccountDto accountDto =  accountService.updateAccountBalance(1, -10.0);
        assertEquals(-10, accountDto.getBalance());

    }
}