package org.veedev.reportservice.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.veedev.reportservice.model.Transaction;
import org.veedev.reportservice.service.ReportService;

@RestController
@RequestMapping("/report")
@AllArgsConstructor
@CrossOrigin
public class ReportController {
    private ReportService reportService;

    @PostMapping("/save-report")
    public void saveReport(@RequestBody Transaction transaction) {
        reportService.saveTransaction(transaction);
    }

    @GetMapping("/download")
    public ResponseEntity<String> downloadReport(@RequestParam String accountNumber, HttpServletResponse response) {
        reportService.generateReport(accountNumber, response);
        return ResponseEntity.ok("Report downloaded successfully");
    }
}
