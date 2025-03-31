package org.veedev.transactionservice.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.veedev.transactionservice.dto.TransactionRequest;
import org.veedev.transactionservice.dto.TransferRequest;
import org.veedev.transactionservice.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Override
    public ResponseEntity<String> deposit(TransactionRequest transactionRequest) {
        return null;
    }

    @Override
    public ResponseEntity<String> withdraw(TransactionRequest transactionRequest) {
        return null;
    }

    @Override
    public ResponseEntity<String> transfer(TransferRequest transferRequest) {
        return null;
    }
}
