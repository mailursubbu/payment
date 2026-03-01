package com.subra.payment.service;

import com.subra.payment.dto.AccountDto;
import com.subra.payment.error.ApplicationException;
import com.subra.payment.mapper.AccountMapper;
import com.subra.payment.mapper.AccountMapperImpl;
import com.subra.payment.model.Account;
import com.subra.payment.repository.AccountRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public final class AccountServiceImpl implements AccountService {
    private final AccountRepo accountRepo;
    private final AccountMapper accountMapper;

    @Override
    public AccountDto save(AccountDto accountDto) {
        Account account = accountMapper.accountDtoToEntity(accountDto);
        account =  accountRepo.save(account);
        return accountMapper.accountEntityToDto(account);
    }

    @Override
    public AccountDto get(int accountId) {
        Optional<Account> optAccount =  accountRepo.getById(accountId);
        Account acc = optAccount.orElseThrow( () -> {
            log.error("Account not found with id {}", accountId);
            return new ApplicationException("No account with id " + accountId);
        } );
        return accountMapper.accountEntityToDto(acc);
    }

}
