package org.veedev.reportservice.service;

import jakarta.servlet.http.HttpServletResponse;
import org.veedev.reportservice.model.Transaction;

import java.util.List;

public interface ReportService {
    void saveTransaction(Transaction transaction);
    void generateReport(String accountNumber, HttpServletResponse response);
    List<Transaction> getTransactions(String accountNumber);
}
