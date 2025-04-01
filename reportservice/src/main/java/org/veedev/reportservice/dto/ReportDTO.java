package org.veedev.reportservice.dto;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.veedev.reportservice.model.Transaction;

@Data
public class ReportDTO {
    private HttpServletResponse response;
    private String accountNumber;
}
