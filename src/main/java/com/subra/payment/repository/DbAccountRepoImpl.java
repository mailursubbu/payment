package com.subra.payment.repository;

import com.subra.payment.error.ApplicationException;
import com.subra.payment.model.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Qualifier("dbAccountRepo")
@Slf4j
@Profile("db")
public class DbAccountRepoImpl implements AccountRepo {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleAccountJdbcInsert;

    private static final String ACCOUNT_QUERY_SQL = "SELECT account_id, document_number, balance, acc_limit FROM account WHERE account_id = :id";
    private static final String UPDATE_ACCOUNT_SQL = "UPDATE account set balance = :balance WHERE account_id = :id";

    private static final String LOCK_ACCOUNT_SQL = "SELECT account_id, document_number, balance, acc_limit FROM account WHERE account_id = :id for update";


    @Override
    public Account save(Account acc) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("document_number", acc.getDocumentId());
        parameters.put("balance", acc.getBalance());
        parameters.put("acc_limit", acc.getAccountLimit());
        Integer id = simpleAccountJdbcInsert.executeAndReturnKey(parameters).intValue();
        acc.setId(id);
        return acc;
    }

    @Override
    public Optional<Account> getById(int id) {
        return getById(id, ACCOUNT_QUERY_SQL);
    }


    public Optional<Account> lockAndGetById(int id) {
        return getById(id, LOCK_ACCOUNT_SQL);
    }

    private Optional<Account> getById(int id, String query) {
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(query,
                    Collections.singletonMap("id", id),
                    (rs, rowNum) -> Account.builder()
                            .id(rs.getInt("account_id"))
                            .documentId(rs.getString("document_number"))
                            .balance(rs.getDouble("balance"))
                            .accountLimit(rs.getDouble("acc_limit"))
                            .build()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApplicationException(String.format("Account with id %d not found", id), e);
        }
    }


    @Override
    public Account updateBalance(int accountId, Double balance) {
        Account account = lockAndGetById(accountId).orElseThrow(() -> new ApplicationException("Account not found with id " + accountId));
        Double updatedBalance = account.getBalance() + balance;
        account.setBalance(updatedBalance);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("balance", updatedBalance);
        parameters.put("id", accountId);
        parameters.put("acc_limit", accountId);
        if( 1 == namedParameterJdbcTemplate.update(UPDATE_ACCOUNT_SQL, parameters) ) {
            return getById(accountId)
                    .orElseThrow(() -> new ApplicationException(String.format("Account with id %d not found", accountId)));
        } else {
            throw new ApplicationException(String.format("Account with id %d not found", accountId));
        }
    }




}
