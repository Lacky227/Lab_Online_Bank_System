package org.veedev.transactionservice.service;

import org.springframework.http.ResponseEntity;
import org.veedev.transactionservice.dto.TransactionRequest;
import org.veedev.transactionservice.dto.TransferRequest;

public interface TransactionService {
    ResponseEntity<String> deposit(TransactionRequest event);
    ResponseEntity<String> withdraw(TransactionRequest event);
    ResponseEntity<String> transfer(TransferRequest event);
}
