package com.subra.payment.service;

import com.subra.payment.dto.AccountDto;
import com.subra.payment.dto.TransactionDto;
import com.subra.payment.error.ApplicationException;
import com.subra.payment.mapper.TransactionMapper;
import com.subra.payment.model.OperationType;
import com.subra.payment.model.Transaction;
import com.subra.payment.repository.TransactionRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {
    @Mock
    TransactionRepo transactionRepo;
    @Mock
    TransactionMapper transactionMapper;
    @Mock
    AccountService accountService;

    @InjectMocks
    TransactionServiceImpl transactionService;

    @Test
    void updateAmountSignWithdraw() {
        Transaction transaction = Transaction.builder()
                .amount(10.0)
                .operationType(OperationType.WITHDRAWAL)
                .accountId(1)
                .build();
        Transaction updatedTxn = transactionService.updateAmount(transaction);
        assertNotNull(updatedTxn);
        assertEquals(-10.0, updatedTxn.getAmount());
    }

    @Test
    void updateAmountSignCreditVoucher() {
        Transaction transaction = Transaction.builder()
                .amount(10.0)
                .operationType(OperationType.CREDIT_VOUCHER)
                .accountId(1)
                .build();
        Transaction updatedTxn = transactionService.updateAmount(transaction);
        assertNotNull(updatedTxn);
        assertEquals(10.0, updatedTxn.getAmount());
    }

    @Test
    void saveWithdrawal() {
        Transaction transaction = Transaction.builder()
                .amount(10.0)
                .operationType(OperationType.WITHDRAWAL)
                .accountId(1)
                .build();

        TransactionDto transactionDto = TransactionDto.builder()
                .amount(10.0)
                .operation_type_id(3)
                .account_id(1)
                .build();

        AccountDto accountDto = AccountDto.builder()
                .account_id(1)
                .document_number("12345")
                .build();

        Mockito.doReturn(transaction).when(transactionMapper).transactionDtoToEntity(Mockito.any());
        Mockito.doReturn(transactionDto).when(transactionMapper).transactionEntityToDto(Mockito.any());
        Mockito.doReturn(accountDto).when(accountService).get(Mockito.anyInt());
        Mockito.doReturn(transaction).when(transactionRepo).createTxn(Mockito.any());

        TransactionDto result = transactionService.save(transactionDto);
        assertNotNull(result);
    }


    @Test
    void getById() {
        Transaction transaction = Transaction.builder()
                .amount(10.0)
                .operationType(OperationType.WITHDRAWAL)
                .accountId(1)
                .build();

        TransactionDto transactionDto = TransactionDto.builder()
                .amount(10.0)
                .operation_type_id(3)
                .account_id(1)
                .build();

        Mockito.doReturn(transactionDto).when(transactionMapper).transactionEntityToDto(Mockito.any());
        Mockito.doReturn(Optional.ofNullable(transaction)).when(transactionRepo).getById(Mockito.anyInt());
        TransactionDto searchResult = transactionService.getById(1);
        assertNotNull(searchResult);
        assertEquals(10.0, searchResult.getAmount());
        assertEquals(OperationType.WITHDRAWAL.getId(), searchResult.getOperation_type_id());
        assertEquals(1, searchResult.getAccount_id());
    }

    @Test
    void getByIdThrowException() {
        Transaction transaction = Transaction.builder()
                .amount(10.0)
                .operationType(OperationType.WITHDRAWAL)
                .accountId(1)
                .build();

        TransactionDto transactionDto = TransactionDto.builder()
                .amount(10.0)
                .operation_type_id(3)
                .account_id(1)
                .build();

        Mockito.doThrow(new ApplicationException("Failed to add transaction as it already exists : ")).when(transactionRepo).getById(Mockito.anyInt());
        assertThrows(ApplicationException.class, () -> transactionService.getById(25));
    }
}