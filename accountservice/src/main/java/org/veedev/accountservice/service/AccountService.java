package org.veedev.accountservice.service;

import org.springframework.http.ResponseEntity;
import org.veedev.accountservice.dto.AccountDetailsRequest;
import org.veedev.accountservice.dto.ClientIdRequest;
import org.veedev.accountservice.dto.CreateAccountRequest;
import org.veedev.accountservice.dto.TransactionRequest;
import org.veedev.accountservice.model.Account;
import org.veedev.accountservice.model.Transaction;

import java.util.List;

public interface AccountService {
    ResponseEntity<String> createAccount(CreateAccountRequest request);
    Transaction updateAccount(TransactionRequest request);
    List<Account> getAccountsByClientId(ClientIdRequest request);
    AccountDetailsRequest getAccountDetails(String number);
}
