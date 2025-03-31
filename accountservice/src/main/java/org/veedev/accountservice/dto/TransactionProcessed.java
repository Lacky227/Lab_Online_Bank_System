package org.veedev.accountservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Data
public class TransactionProcessed {
    private String transactionId;
    private String lastName;
    private String numberAccount;
    private BigDecimal amount;
    private Currency currency;
    private String transactionType;
    private LocalDateTime timestamp;
}
