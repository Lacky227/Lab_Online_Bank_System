package org.veedev.reportservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal amount;
    private String currency;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private TransactionType transactionType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;
}
