package org.veedev.transactionservice.service;

import org.veedev.transactionservice.dto.TransactionRequest;
import org.veedev.transactionservice.model.Transaction;

public interface KafkaService {
    void requestTransaction(TransactionRequest event);
    void handleProcessedTransaction(Transaction transaction);
}
