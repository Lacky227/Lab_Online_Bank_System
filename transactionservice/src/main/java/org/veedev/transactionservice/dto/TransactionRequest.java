package org.veedev.transactionservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequest {
    @JsonProperty("accountNumber")
    private String accountNumber;
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("transactionType")
    private String transactionType;
}
