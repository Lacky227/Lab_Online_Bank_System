package org.veedev.accountservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequested {
    private String accountNumber;
    private String transactionType;
    private BigDecimal amount;
}
