package org.veedev.reportservice.impl;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.veedev.reportservice.dto.ReportDTO;
import org.veedev.reportservice.model.Transaction;
import org.veedev.reportservice.repository.ReportRepository;
import org.veedev.reportservice.service.ReportService;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    @Override
    public void saveTransaction(Transaction transaction) {
        reportRepository.save(transaction);
    }

    @Override
    public void generateReport(ReportDTO reportDTO) {
        reportDTO.getResponse().setContentType("application/pdf");
        reportDTO.getResponse().setHeader("Content-Disposition", "attachment; filename=transaction.pdf");
        try {
            PdfWriter pdfWriter =new PdfWriter(reportDTO.getResponse().getOutputStream());
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument);

            document.add(new Paragraph("Transaction Report").setFontSize(16));
            List<Transaction> transactions = reportRepository.findByNumberAccount(reportDTO.getAccountNumber());
            if (transactions.isEmpty()) {
                reportDTO.getResponse().setStatus(HttpStatus.NO_CONTENT.value());
                document.add(new Paragraph("No transactions found"));
                document.close();
                return;
            }
            float[] columnWidths = {100f, 100f, 100f, 100f, 100f, 150f};
            Table table = new Table(UnitValue.createPointArray(columnWidths)).useAllAvailableWidth();

            table.addHeaderCell(new Cell().add(new Paragraph("Account Number: ")));
            table.addHeaderCell(new Cell().add(new Paragraph("Last Name: ")));
            table.addHeaderCell(new Cell().add(new Paragraph("Amount: ")));
            table.addHeaderCell(new Cell().add(new Paragraph("Currency: ")));
            table.addHeaderCell(new Cell().add(new Paragraph("Type: ")));
            table.addHeaderCell(new Cell().add(new Paragraph("Transaction Date: ")));
            for (Transaction transaction : transactions) {
                table.addCell(new Cell().add(new Paragraph(transaction.getNumberAccount())));
                table.addCell(new Cell().add(new Paragraph(transaction.getLastName())));
                table.addCell(new Cell().add(new Paragraph(transaction.getAmount().toString())));
                table.addCell(new Cell().add(new Paragraph(transaction.getCurrency())));
                table.addCell(new Cell().add(new Paragraph(transaction.getTransactionType().toString())));
                table.addCell(new Cell().add(new Paragraph(transaction.getTimestamp().toString())));
            }
            document.add(table);
            document.close();
            reportDTO.getResponse().getOutputStream().flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
