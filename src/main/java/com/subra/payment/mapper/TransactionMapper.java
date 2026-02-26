package com.subra.payment.mapper;

import com.subra.payment.dto.TransactionDto;
import com.subra.payment.model.Transaction;

public interface TransactionMapper {

    Transaction transactionDtoToEntity(TransactionDto transactionDto);
    TransactionDto transactionEntityToDto(Transaction transaction);
}
