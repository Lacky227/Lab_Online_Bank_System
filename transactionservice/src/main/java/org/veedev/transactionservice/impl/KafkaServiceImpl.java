package org.veedev.transactionservice.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.veedev.transactionservice.dto.TransactionRequest;
import org.veedev.transactionservice.model.Transaction;
import org.veedev.transactionservice.service.KafkaService;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaServiceImpl implements KafkaService {
    private KafkaTemplate<String, Object> kafkaTemplate;
    @Override
    public void requestTransaction(TransactionRequest event) {
        log.info("Requesting transaction: {}", event);
        kafkaTemplate.send("transaction-to-account", event);
    }

    @Override
    @KafkaListener(topics = "account-to-transaction", groupId = "transaction-group")
    public void handleProcessedTransaction(Transaction transaction) {
        kafkaTemplate.send("transaction-completed", transaction);
    }
}
