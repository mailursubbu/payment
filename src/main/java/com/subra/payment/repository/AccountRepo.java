package com.subra.payment.repository;


import com.subra.payment.model.Account;

import java.util.Optional;


public interface AccountRepo {
    Account save(Account acc);
    Optional<Account> getById(int id);
}
