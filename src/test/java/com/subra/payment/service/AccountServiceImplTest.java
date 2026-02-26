package com.subra.payment.service;

import com.subra.payment.dto.AccountDto;
import com.subra.payment.mapper.AccountMapperImpl;
import com.subra.payment.repository.AccountRepo;
import com.subra.payment.repository.AccountRepoImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceImplTest {

    AccountRepo accountRepo = new AccountRepoImpl();
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

}