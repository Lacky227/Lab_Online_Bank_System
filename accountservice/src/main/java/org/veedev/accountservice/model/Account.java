package org.veedev.accountservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long clientId;

    @Column(unique = true, nullable = false)
    private String number;

    @Column(nullable = false)
    private CurrencyType currency;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private String pinCode;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private LocalDateTime endAt;
}
