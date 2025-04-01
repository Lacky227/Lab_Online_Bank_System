package org.veedev.accountservice.dto;

import lombok.Data;

@Data
public class CreateAccountRequest {
    private String phoneNumber;
    private String currency;
}
