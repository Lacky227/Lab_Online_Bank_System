package org.veedev.transactionservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;

@Data
public class Transaction {
    private String transactionId;
    private String lastName;
    private String numberAccount;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal amount;
    private String currency;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private TransactionType transactionType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;
}
