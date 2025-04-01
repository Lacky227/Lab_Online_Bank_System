package org.veedev.reportservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.veedev.reportservice.dto.ReportDTO;
import org.veedev.reportservice.model.Transaction;
import org.veedev.reportservice.service.ReportService;

@RestController
@RequestMapping("/report")
@AllArgsConstructor
@CrossOrigin
public class ReportController {
    private ReportService reportService;

    @PostMapping("/save-report")
    public ResponseEntity<String> saveReport(@RequestBody Transaction transaction) {
        reportService.saveTransaction(transaction);
        return ResponseEntity.ok("Report saved successfully");
    }

    @GetMapping("/download")
    public ResponseEntity<String> downloadReport(@RequestBody ReportDTO reportDTO) {
        reportService.generateReport(reportDTO);
        return ResponseEntity.ok("Report downloaded successfully");
    }
}
