package com.subra.payment.mapper;

import com.subra.payment.dto.AccountDto;
import com.subra.payment.model.Account;


public interface AccountMapper {
    AccountDto accountEntityToDto(Account account);
    Account accountDtoToEntity(AccountDto accountDto);
}
