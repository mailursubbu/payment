package com.subra.payment.repository;

import com.subra.payment.error.ApplicationException;
import com.subra.payment.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Transaction Repo based on Java Collection Set
 */
@Component
@Slf4j
public class TransactionRepoImpl implements TransactionRepo {

    //Thread safe set
    Set<Transaction> transactionSet = new ConcurrentSkipListSet<>();
    //Thread safe id creation
    AtomicInteger txnId = new AtomicInteger(0);

    @Override
    public Transaction createTxn(Transaction transaction) {
        if(transaction.getId() == null) {
            transaction.setId(txnId.incrementAndGet());
            transaction.setEventDate(LocalDateTime.now());
        }
        if(transactionSet.add(transaction) ) {
            return transaction;
        } else {
            log.info("Failed to add transaction as it already exists : " + transaction);
            throw new ApplicationException("Failed to add transaction as it already exists : " + transaction);
        }
    }

    public Optional<Transaction> getById(int id) {
         return transactionSet.stream().filter(t -> t.getId() == id).findFirst();
    }
}
