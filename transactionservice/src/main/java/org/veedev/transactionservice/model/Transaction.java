package org.veedev.transactionservice.model;

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
    private BigDecimal amount;
    private Currency currency;
    private TransactionType transactionType;
    private LocalDateTime timestamp;
}
