package org.veedev.accountservice.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.veedev.accountservice.dto.TransactionProcessed;
import org.veedev.accountservice.dto.TransactionRequested;
import org.veedev.accountservice.model.Account;
import org.veedev.accountservice.repository.AccountRepository;
import org.veedev.accountservice.service.KafkaService;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class KafkaServiceImpl implements KafkaService {
    private final KafkaTemplate<String, TransactionProcessed> kafkaTemplate;
    private final RedisTemplate<String, String> redisTemplate;
    private AccountRepository accountRepository;

    @Override
    @KafkaListener(topics = "transaction-requested", groupId = "account-group")
    public void processTransaction(TransactionRequested event) {
        Account account = accountRepository.findByNumber(event.getAccountNumber());
        if (account == null) {
            throw new RuntimeException("Account not found");
        } else if (Objects.equals(event.getTransactionType(), "DEPOSIT")) {
            account.setBalance(account.getBalance().add(event.getAmount()));
        } else if (Objects.equals(event.getTransactionType(), "WITHDRAW")) {
            if (account.getBalance().compareTo(event.getAmount()) < 0) {
                throw new RuntimeException("Account balance is lower than account balance");
            }
            account.setBalance(account.getBalance().subtract(event.getAmount()));
        }
        accountRepository.save(account);
        String currency = account.getCurrency().toString();
        String cacheLastName = redisTemplate.opsForValue().get("session lastName:" + account.getId());
        TransactionProcessed transactionProcessed = new TransactionProcessed();
        transactionProcessed.setTransactionId(UUID.randomUUID().toString());
        transactionProcessed.setLastName(cacheLastName);
        transactionProcessed.setNumberAccount(account.getNumber());
        transactionProcessed.setAmount(event.getAmount());
        transactionProcessed.setCurrency(account.getCurrency());
        transactionProcessed.setTransactionType(event.getTransactionType());
        transactionProcessed.setTimestamp(LocalDateTime.now());
        kafkaTemplate.send("transaction-processed", transactionProcessed);
    }
}
