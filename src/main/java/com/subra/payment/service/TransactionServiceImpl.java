package com.subra.payment.service;

import com.subra.payment.dto.TransactionDto;
import com.subra.payment.error.ApplicationException;
import com.subra.payment.mapper.TransactionMapper;
import com.subra.payment.model.OperationType;
import com.subra.payment.model.Transaction;
import com.subra.payment.repository.TransactionRepo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

@Data
@RequiredArgsConstructor
@Component
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionMapper transactionMapper;
    private final TransactionRepo transactionRepo;
    private final AccountService accountService;


    @Override
    public TransactionDto save(TransactionDto transactionDto) {
        Transaction transaction = transactionMapper.transactionDtoToEntity(transactionDto);
        //Validate if the account_id is present
        accountService.get(transaction.getAccountId());
        transaction = updateAmount(transaction);
        transaction = transactionRepo.createTxn(transaction);
        return transactionMapper.transactionEntityToDto(transaction);
    }

    //Update amount sign based on the operation type
    protected Transaction updateAmount(Transaction transaction) {
        OperationType operationType =  transaction.getOperationType();
        transaction.setAmount(transaction.getAmount() * operationType.getAmountMultiplier());
        return transaction;
    }

    @Override
    public TransactionDto getById(int id) {
        Optional<Transaction> optTransaction =  transactionRepo.getById(id);
        Transaction txn = optTransaction.orElseThrow( () -> {
            log.error("Transaction not found with id {}", id);
            return new ApplicationException("Transaction not found with id " + id);
        } );
        return transactionMapper.transactionEntityToDto(txn);
    }
}
