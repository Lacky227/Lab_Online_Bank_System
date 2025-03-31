package org.veedev.transactionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.veedev.transactionservice.model.TransactionType;

import java.math.BigDecimal;

@Data
public class TransactionRequest {
    private String accountNumber;
    private TransactionType transactionType;
    private BigDecimal amount;
}
