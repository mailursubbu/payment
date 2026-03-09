package com.subra.payment.service;

import com.subra.payment.dto.AccountDto;
import com.subra.payment.error.ApplicationException;
import com.subra.payment.mapper.AccountMapper;
import com.subra.payment.model.Account;
import com.subra.payment.repository.AccountRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public final class AccountServiceImpl implements AccountService {

    private final AccountRepo accountRepo;
    private final AccountMapper accountMapper;

    public AccountServiceImpl (@Autowired AccountRepo accountRepo, @Autowired  AccountMapper accountMapper) {
        this.accountRepo = accountRepo;
        this.accountMapper = accountMapper;
    }

    @Override
    public AccountDto save(AccountDto accountDto) {
        Account account = accountMapper.accountDtoToEntity(accountDto);
        account.setBalance(0.0);
        account.setAccountLimit(-1000.0);
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

    public AccountDto updateAccountBalance(int accountId, Double balance) {
        Account account = accountRepo.updateBalance(accountId, balance);
        if (account.getBalance() < account.getAccountLimit()) {
            throw new ApplicationException("Account balance is less than the account limit of  "+ account.getAccountLimit());
        }
        return accountMapper.accountEntityToDto(account);
    }

}
