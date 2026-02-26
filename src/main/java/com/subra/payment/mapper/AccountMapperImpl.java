package com.subra.payment.mapper;

import com.subra.payment.dto.AccountDto;
import com.subra.payment.model.Account;
import org.springframework.stereotype.Component;

/**
 * Simple mapper for Entity to DTO and vice versa
 */
@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public AccountDto accountEntityToDto(Account account) {
        AccountDto accountDto =  AccountDto
                .builder()
                .account_id(account.getId())
                .document_number(account.getDocumentId())
                .build();
        return accountDto;
    }

    @Override
    public Account accountDtoToEntity(AccountDto accountDto) {
        Account account = Account.builder()
                .id(accountDto.getAccount_id())
                .documentId(accountDto.getDocument_number())
                .build();
        return account;
    }
}
