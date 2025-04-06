package org.veedev.accountservice.dto;

import lombok.Data;
import org.veedev.accountservice.model.CurrencyType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AccountDetailsRequest {
    private String number;
    private CurrencyType currency;
    private BigDecimal balance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime endAt;
}
