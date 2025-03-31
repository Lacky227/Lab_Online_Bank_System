package org.veedev.transactionservice.impl;

import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.veedev.transactionservice.dto.TransactionRequest;
import org.veedev.transactionservice.service.KafkaService;

@Service
@AllArgsConstructor
public class KafkaServiceImpl implements KafkaService {
    private KafkaTemplate<String, TransactionRequest> kafkaTemplate;
    @Override
    public void requestTransaction(TransactionRequest event) {
        kafkaTemplate.send("transaction-requested", event);
    }

    @Override
    @KafkaListener(topics = "transaction-processed", groupId = "transaction-group")
    public void handleProcessedTransaction(TransactionRequest event) {
        kafkaTemplate.send("transaction-completed", event);
    }
}
