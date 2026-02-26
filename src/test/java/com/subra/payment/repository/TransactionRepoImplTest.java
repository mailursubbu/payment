package com.subra.payment.repository;

import com.subra.payment.model.OperationType;
import com.subra.payment.model.Transaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionRepoImplTest {


    Transaction prepareTransaction(OperationType operationType ) {
        TransactionRepoImpl  transactionRepo = new TransactionRepoImpl();
        Transaction transaction = Transaction.builder()
                .amount(10.0)
                .operationType(operationType)
                .accountId(1)
                .build();
        transactionRepo.createTxn(transaction);
        return transaction;
    }

    void validateTransaction(Transaction transaction) {
        assertEquals(10, transaction.getAmount());
        assertNotNull(transaction.getId());
        assertEquals(OperationType.WITHDRAWAL, transaction.getOperationType());
        assertEquals(1, transaction.getAccountId());
    }

    @Test
    void createTxnWithdrawal() {
        Transaction  transaction =  prepareTransaction(OperationType.WITHDRAWAL);
        TransactionRepoImpl  transactionRepo = new TransactionRepoImpl();
        transaction = transactionRepo.createTxn(transaction);


    }

    @Test
    void getById() {
    }
}