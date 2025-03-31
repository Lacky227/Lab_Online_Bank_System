package org.veedev.transactionservice.service;

import org.veedev.transactionservice.dto.TransactionRequest;

public interface KafkaService {
    void requestTransaction(TransactionRequest event);
    void handleProcessedTransaction(TransactionRequest event);
}
