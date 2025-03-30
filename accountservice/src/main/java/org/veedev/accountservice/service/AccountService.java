package org.veedev.accountservice.service;

import org.springframework.http.ResponseEntity;
import org.veedev.accountservice.dto.CreateAccountRequest;
import org.veedev.accountservice.model.Account;

public interface AccountService {
    ResponseEntity<String> createAccount(CreateAccountRequest request);
}
