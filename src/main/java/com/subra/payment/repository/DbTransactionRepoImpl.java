package com.subra.payment.repository;

import com.subra.payment.model.OperationType;
import com.subra.payment.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Qualifier("dbTransactionRepo")
@Profile("db")
public class DbTransactionRepoImpl implements TransactionRepo {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert simpleTransactionJdbcInsert;

    private static final String TRANSACTION_QUERY_SQL = "SELECT transaction_id, account_id,operation_type_id, amount, event_date FROM transaction WHERE transaction_id = :id";


    @Override
    public Transaction createTxn(Transaction transaction) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("account_id", transaction.getAccountId());
        parameters.put("operation_type_id", transaction.getOperationType().getId());
        parameters.put("amount", transaction.getAmount());
        parameters.put("event_date", Timestamp.valueOf(LocalDateTime.now()));
        Integer id = simpleTransactionJdbcInsert.executeAndReturnKey(parameters).intValue();
        transaction.setId(id);
        return transaction;
    }

    @Override
    public Optional<Transaction> getById(int id) {
        return Optional.ofNullable( namedParameterJdbcTemplate.queryForObject(TRANSACTION_QUERY_SQL,
                Collections.singletonMap("id", id),
                (rs, rowNum) -> Transaction.builder()
                        .id(rs.getInt("transaction_id"))
                        .accountId(rs.getInt("account_id"))
                        .operationType(OperationType.fromId(rs.getInt("operation_type_id")))
                        .amount(rs.getDouble("amount"))
                        .eventDate(rs.getTimestamp("event_date").toLocalDateTime())
                        .build()));
    }
}
