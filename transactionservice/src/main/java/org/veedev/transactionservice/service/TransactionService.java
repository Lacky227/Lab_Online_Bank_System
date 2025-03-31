package org.veedev.transactionservice.service;

import org.springframework.http.ResponseEntity;
import org.veedev.transactionservice.dto.TransactionRequest;
import org.veedev.transactionservice.dto.TransferRequest;

public interface TransactionService {
    ResponseEntity<String> deposit(TransactionRequest transactionRequest);
    ResponseEntity<String> withdraw(TransactionRequest transactionRequest);
    ResponseEntity<String> transfer(TransferRequest transferRequest);
}
