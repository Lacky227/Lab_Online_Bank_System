package org.veedev.transactionservice.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.veedev.transactionservice.dto.TransactionRequest;
import org.veedev.transactionservice.dto.TransferRequest;
import org.veedev.transactionservice.model.TransactionType;
import org.veedev.transactionservice.service.KafkaService;
import org.veedev.transactionservice.service.TransactionService;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final KafkaService kafkaService;
    private final KafkaTemplate<String, TransactionRequest> kafkaTemplate;
    @Override
    public ResponseEntity<String> deposit(TransactionRequest event) {
        event.setTransactionType(TransactionType.DEPOSIT);
        kafkaService.requestTransaction(event);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<String> withdraw(TransactionRequest event) {
        event.setTransactionType(TransactionType.WITHDRAW);
        kafkaService.requestTransaction(event);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<String> transfer(TransferRequest event) {
        return null;
    }
}
