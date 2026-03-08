package com.subra.payment.repository;

import com.subra.payment.error.ApplicationException;
import com.subra.payment.lock.LockService;
import com.subra.payment.model.Account;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Account Repo implemented on top of Java Collection Set
 */
@Repository
@RequiredArgsConstructor
@Slf4j
@Profile("memory")
public class AccountRepoImpl implements AccountRepo {

    private final LockService  lockService;
    // Thread safe automic integer for creating id
    AtomicInteger newId = new AtomicInteger(0);

    //Thread safe set
    Set<Account> accounts = new ConcurrentSkipListSet<>();

    private int getNewId() {
        return newId.incrementAndGet();
    }

    @Override
    public Account save(Account acc) {

        if( acc.getId() == null ) {
            acc.setId(getNewId());
        }
        if( accounts.add(acc) ) {
            log.info("Account save successful");
        } else {
            log.info("Account already exists");
            accounts.remove(acc);
            accounts.add(acc);
        }

        return acc;
    }

    @Override
    public Optional<Account> getById(int id) {
        return accounts
                .stream()
                .filter(acc -> acc.getId() == id)
                .findFirst();
    }

    @Override
    public Account updateBalance(int accountId, Double balance) {
        try {
            lockService.acquireLock(accountId);
            Account account = accounts.stream().filter(acc -> acc.getId() == accountId).findFirst()
                    .orElseThrow(() -> {
                        log.error("Account update failed as account {} not found", accountId);
                        return new ApplicationException("Account " + accountId + " is not found");
                    });
            account.setBalance(account.getBalance() + balance);
            return account;
        } finally {
            lockService.releaseLock(accountId);
        }
    }

}
