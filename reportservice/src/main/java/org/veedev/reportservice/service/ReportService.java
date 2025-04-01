package org.veedev.reportservice.service;

import jakarta.servlet.http.HttpServletResponse;
import org.veedev.reportservice.dto.ReportDTO;
import org.veedev.reportservice.model.Transaction;

public interface ReportService {
    void saveTransaction(ReportDTO reportDTO);
}
