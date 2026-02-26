package com.subra.payment.repository;

import com.subra.payment.model.Transaction;

import java.util.Optional;

public interface TransactionRepo {
    Transaction createTxn(Transaction transaction);
    public Optional<Transaction> getById(int id);
}
