package com.subra.payment.service;

import com.subra.payment.dto.TransactionDto;

public interface TransactionService {
    TransactionDto save(TransactionDto transactionDto);
    TransactionDto getById(int id);
}
