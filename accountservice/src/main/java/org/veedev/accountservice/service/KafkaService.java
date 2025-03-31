package org.veedev.accountservice.service;

import org.veedev.accountservice.dto.TransactionRequested;

public interface KafkaService {
    void processTransaction(TransactionRequested event);
}
