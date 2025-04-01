package org.veedev.reportservice.service;

import org.veedev.reportservice.dto.ReportDTO;
import org.veedev.reportservice.model.Transaction;

public interface ReportService {
    void saveTransaction(Transaction transaction);
    void generateReport(ReportDTO reportDTO);
}
