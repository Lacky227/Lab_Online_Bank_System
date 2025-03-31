package org.veedev.accountservice.dto;

import lombok.Data;
import org.veedev.accountservice.model.CurrencyType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Data
public class TransactionProcessed {
    private String transactionId;
    private String lastName;
    private String numberAccount;
    private BigDecimal amount;
    private CurrencyType currency;
    private String transactionType;
    private LocalDateTime timestamp;
}
