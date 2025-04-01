package org.veedev.accountservice.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.veedev.accountservice.dto.Transaction;
import org.veedev.accountservice.dto.TransactionRequest;
import org.veedev.accountservice.model.Account;
import org.veedev.accountservice.repository.AccountRepository;
import org.veedev.accountservice.service.KafkaService;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaServiceImpl implements KafkaService {
    private final KafkaTemplate<Object, Transaction> kafkaTemplate;
    private final RedisTemplate<String, String> redisTemplate;
    private AccountRepository accountRepository;

    @Override
    @KafkaListener(topics = "transaction-requested", groupId = "account-group")
    public void processTransaction(TransactionRequest event) {
        log.info("Received transaction requested: {}", event);
        Account account = accountRepository.findByNumber(event.getAccountNumber());
        if (account == null) {
            log.error("Account not found: {}", event.getAccountNumber());
            throw new RuntimeException("Account not found");
        } else if (Objects.equals(event.getType(), "DEPOSIT")) {
            log.info("Depositing transaction");
            account.setBalance(account.getBalance().add(event.getAmount()));
        } else if (Objects.equals(event.getType(), "WITHDRAW")) {
            log.info("Withdrawing transaction");
            if (account.getBalance().compareTo(event.getAmount()) < 0) {
                log.error("Account balance is lower than account balance");
                throw new RuntimeException("Account balance is lower than account balance");
            }
            account.setBalance(account.getBalance().subtract(event.getAmount()));
        }
        log.info("Account balance: {}", account.getBalance());
        accountRepository.save(account);
        log.info("Account balance: {}", account.getBalance());
        String cacheLastName = redisTemplate.opsForValue().get("session lastName:" + account.getId());
        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setLastName(cacheLastName);
        transaction.setNumberAccount(account.getNumber());
        transaction.setAmount(event.getAmount());
        transaction.setCurrency(account.getCurrency());
        transaction.setTransactionType(event.getType());
        transaction.setTimestamp(LocalDateTime.now());
        kafkaTemplate.send("account-to-transaction", transaction);
    }
}
