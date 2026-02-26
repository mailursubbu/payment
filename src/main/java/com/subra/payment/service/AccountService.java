package com.subra.payment.service;

import com.subra.payment.dto.AccountDto;

public interface AccountService {
    AccountDto save(AccountDto accountDto);
    AccountDto get(int accountId );
}
