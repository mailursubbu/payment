package com.subra.payment.repository;

import com.subra.payment.error.ApplicationException;
import com.subra.payment.model.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class DbAccountRepoImpl implements AccountRepo {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleAccountJdbcInsert;

    private static final String ACCOUNT_QUERY_SQL = "SELECT account_id, document_number FROM account WHERE account_id = :id";

    @Override
    public Account save(Account acc) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("document_number", acc.getDocumentId());
        Integer id = simpleAccountJdbcInsert.executeAndReturnKey(parameters).intValue();
        acc.setId(id);
        return acc;
    }

    @Override
    public Optional<Account> getById(int id) {
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(ACCOUNT_QUERY_SQL,
                    Collections.singletonMap("id", id),
                    (rs, rowNum) -> Account.builder()
                            .id(rs.getInt("account_id"))
                            .documentId(rs.getString("document_number"))
                            .build()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApplicationException(String.format("Account with id %d not found", id), e);
        }
    }
}
