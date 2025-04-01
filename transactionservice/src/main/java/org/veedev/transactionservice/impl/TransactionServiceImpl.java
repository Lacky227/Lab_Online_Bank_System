package org.veedev.transactionservice.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.veedev.transactionservice.dto.TransactionRequest;
import org.veedev.transactionservice.dto.TransferRequest;
import org.veedev.transactionservice.service.RestService;
import org.veedev.transactionservice.service.TransactionService;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final RestService restService;

    @Override
    public ResponseEntity<String> deposit(TransactionRequest transactionRequest) {
        restService.processTransaction(transactionRequest);
        return ResponseEntity.status(HttpStatus.OK).body("Transaction deposited successfully");
    }

    @Override
    public ResponseEntity<String> withdraw(TransactionRequest transactionRequest) {
        restService.processTransaction(transactionRequest);
        return ResponseEntity.status(HttpStatus.OK).body("Transaction withdrawn successfully");
    }
}
