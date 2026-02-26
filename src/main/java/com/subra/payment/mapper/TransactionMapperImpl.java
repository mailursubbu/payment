package com.subra.payment.mapper;

import com.subra.payment.dto.TransactionDto;
import com.subra.payment.model.OperationType;
import com.subra.payment.model.Transaction;
import org.springframework.stereotype.Component;

/**
 * Simple mapper
 */
@Component
public class TransactionMapperImpl implements TransactionMapper {
    @Override
    public Transaction transactionDtoToEntity(TransactionDto transactionDto) {
        OperationType operationType = OperationType.fromId(transactionDto.getOperation_type_id());

        return Transaction.builder()
                .id(transactionDto.getTransaction_id())
                .operationType(operationType)
                .accountId(transactionDto.getAccount_id())
                .amount(transactionDto.getAmount())
                .eventDate(transactionDto.getEventDate())
                .build();
    }

    @Override
    public TransactionDto transactionEntityToDto(Transaction transaction) {

        return TransactionDto.builder()
                .transaction_id(transaction.getId())
                .account_id(transaction.getAccountId())
                .amount(transaction.getAmount())
                .operation_type_id(transaction.getOperationType().getId())
                .eventDate(transaction.getEventDate())
                .build();
    }
}
