package org.veedev.transactionservice.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.veedev.transactionservice.dto.TransactionRequest;
import org.veedev.transactionservice.model.Transaction;
import org.veedev.transactionservice.service.RestService;

@Service
@RequiredArgsConstructor
public class RestServiceImpl implements RestService {
    @Value("${accountService.url}")
    private String accountUrl;
    @Value("${reportService.url}")
    private String reportUrl;
    private final RestTemplate restTemplate;
    @Override
    public void processTransaction(TransactionRequest transactionRequest) {
        Transaction transaction = restTemplate.postForObject(accountUrl, transactionRequest, Transaction.class);
        setTransactionToReportService(transaction);
    }

    @Override
    public void setTransactionToReportService(Transaction transaction) {
        restTemplate.postForObject(reportUrl, transaction, Transaction.class);
    }
}
