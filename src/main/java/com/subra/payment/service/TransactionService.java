package com.subra.payment.service;

import com.subra.payment.dto.TransactionDto;

public sealed interface TransactionService permits TransactionServiceImpl {
    TransactionDto save(TransactionDto transactionDto);
    TransactionDto getById(int id);
}
