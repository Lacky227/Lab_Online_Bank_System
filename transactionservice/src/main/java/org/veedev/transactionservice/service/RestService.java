package org.veedev.transactionservice.service;

import org.veedev.transactionservice.dto.TransactionRequest;
import org.veedev.transactionservice.model.Transaction;

public interface RestService {
    void processTransaction(TransactionRequest transactionRequest);
    void setTransactionToReportService(Transaction transaction);
}
