package org.veedev.reportservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Document
public class Transaction {
    @Id
    private String transactionId;
    private String lastName;
    private String numberAccount;
    private BigDecimal amount;
    private String currency;
    private TransactionType transactionType;
    private LocalDateTime timestamp;
}
