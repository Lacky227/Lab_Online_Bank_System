package org.veedev.transactionservice.dto;

import lombok.Data;
import org.veedev.transactionservice.model.TransactionType;

import java.math.BigDecimal;

@Data
public class TransactionRequest {
    private String accountNumber;
    private BigDecimal amount;
    private TransactionType transactionType;
}
