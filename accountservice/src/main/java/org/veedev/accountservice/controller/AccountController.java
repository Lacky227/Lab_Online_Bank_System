package org.veedev.accountservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.veedev.accountservice.dto.CreateAccountRequest;
import org.veedev.accountservice.dto.TransactionRequest;
import org.veedev.accountservice.model.Transaction;
import org.veedev.accountservice.service.AccountService;

@RestController
@RequestMapping("/account")
@AllArgsConstructor
@CrossOrigin
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/create")
    ResponseEntity<String> create(@RequestBody CreateAccountRequest createAccountRequest){
        return accountService.createAccount(createAccountRequest);
    }
    @PostMapping("/update-balance")
    Transaction updateBalance(@RequestBody TransactionRequest transactionRequest){
        return accountService.updateAccount(transactionRequest);
    }
}
