package org.veedev.accountservice.service;

import org.veedev.accountservice.dto.TransactionRequest;

public interface KafkaService {
    void processTransaction(TransactionRequest event);
}
