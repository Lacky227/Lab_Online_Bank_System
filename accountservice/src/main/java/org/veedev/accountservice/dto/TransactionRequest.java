package org.veedev.accountservice.dto;

import lombok.Data;
import org.veedev.accountservice.model.TransactionType;
import java.math.BigDecimal;

@Data
public class TransactionRequest {
    private String accountNumber;
    private BigDecimal amount;
    private String phoneNumber;
    private TransactionType transactionType;
}
