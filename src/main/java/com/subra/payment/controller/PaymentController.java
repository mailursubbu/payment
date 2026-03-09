package com.subra.payment.controller;

import com.subra.payment.dto.AccountDto;
import com.subra.payment.dto.TransactionDto;
import com.subra.payment.repository.AccountRepo;
import com.subra.payment.service.AccountService;
import com.subra.payment.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    final private AccountService accountService;
    final private TransactionService transactionService;

    @GetMapping("/account/{id}")
    public AccountDto getAccount(@PathVariable Integer id) {
        return accountService.get(id);
    }

    @PostMapping("/account")
    public AccountDto createAccount(@RequestBody AccountDto accountDto ) {
        return accountService.save(accountDto);
    }

    @PostMapping("/transactions")
    @Transactional
    public TransactionDto createTxn(@RequestBody TransactionDto transactionDto ) {
        TransactionDto transactionDto1 = transactionService.save(transactionDto);
        accountService.updateAccountBalance(transactionDto1.getAccount_id(), transactionDto1.getAmount());

        return transactionDto1;
    }

    @GetMapping("/transactions/{id}")
    public TransactionDto getTxnById(@PathVariable Integer id ) {
        return transactionService.getById(id);
    }

}
