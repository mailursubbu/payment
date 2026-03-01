package com.subra.payment.service;

import com.subra.payment.dto.AccountDto;

public sealed interface AccountService permits AccountServiceImpl {
    AccountDto save(AccountDto accountDto);
    AccountDto get(int accountId );
}
