package org.veedev.transactionservice.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.veedev.transactionservice.dto.TransactionRequest;
import org.veedev.transactionservice.dto.TransferRequest;
import org.veedev.transactionservice.service.KafkaService;
import org.veedev.transactionservice.service.TransactionService;

@Service
@AllArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private final KafkaService kafkaService;
    @Override
    public ResponseEntity<String> deposit(TransactionRequest event) {
        log.info("Depositing transaction: {}", event);
        kafkaService.requestTransaction(event);
        log.info("Successfully deposited transaction: {}", event);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<String> withdraw(TransactionRequest event) {
        kafkaService.requestTransaction(event);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<String> transfer(TransferRequest event) {
        return null;
    }
}
