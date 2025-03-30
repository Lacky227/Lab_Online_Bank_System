package org.veedev.accountservice.dto;

import lombok.Data;
import org.veedev.accountservice.model.CurrencyType;

@Data
public class CreateAccountRequest {
    private String phoneNumber;
    private CurrencyType currency;
}
