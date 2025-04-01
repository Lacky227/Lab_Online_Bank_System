package org.veedev.transactionservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequest {
    private String fromAccountNumber;
    private String toAccountNumber;
    private String fromLastName;
    private String toLastName;
    private BigDecimal amount;
    private String transactionType;
}
