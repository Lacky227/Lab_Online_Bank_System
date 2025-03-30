package org.veedev.accountservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.veedev.accountservice.dto.CreateAccountRequest;
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
}
