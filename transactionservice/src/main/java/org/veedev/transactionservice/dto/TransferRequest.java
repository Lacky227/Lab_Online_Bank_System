package org.veedev.transactionservice.dto;

import lombok.Data;
import org.veedev.transactionservice.model.TransactionType;

import java.math.BigDecimal;

@Data
public class TransferRequest {
    private String fromAccountNumber;
    private String toAccountNumber;
    private String fromLastName;
    private String toLastName;
    private BigDecimal amount;
    private TransactionType transactionType;
}
