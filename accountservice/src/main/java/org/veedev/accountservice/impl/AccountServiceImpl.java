package org.veedev.accountservice.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.veedev.accountservice.dto.AccountDetailsRequest;
import org.veedev.accountservice.dto.ClientIdRequest;
import org.veedev.accountservice.dto.CreateAccountRequest;
import org.veedev.accountservice.dto.TransactionRequest;
import org.veedev.accountservice.model.Account;
import org.veedev.accountservice.model.Transaction;
import org.veedev.accountservice.model.TransactionType;
import org.veedev.accountservice.repository.AccountRepository;
import org.veedev.accountservice.service.AccountService;
import org.veedev.accountservice.util.AccountUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, String> redisTemplate;
    private final RestTemplate restTemplate;
    @Override
    public ResponseEntity<String> createAccount(CreateAccountRequest request) {
        String cacheKey = "session id:" + request.getPhoneNumber();
        String cacheValue = redisTemplate.opsForValue().get(cacheKey);
        if (cacheValue == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Account creation failed, phone number is incorrect");
        }
        Long clientId = Long.parseLong(cacheValue);
        List<Account> accounts = accountRepository.findByClientId(clientId);
        for (Account account : accounts) {
            if (account.getCurrency().equals(request.getCurrency())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Account already exists");
            }
        }
        Account account = new Account();
        account.setClientId(clientId);
        account.setNumber(AccountUtil.generateAccountNumber());
        account.setCurrency(request.getCurrency());
        account.setBalance(BigDecimal.ZERO);
        String pinCode = AccountUtil.generatePinCode();
        account.setPinCode(passwordEncoder.encode(pinCode));
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        account.setEndAt(LocalDateTime.now().plusYears(5));
        accountRepository.save(account);
        return ResponseEntity.status(HttpStatus.CREATED).body("Account created successfully, your account number is: " + account.getNumber() + ", pin code is: " + pinCode + ", and account end is: " + account.getEndAt());
    }

    @Override
    public Transaction updateAccount(TransactionRequest request) {
        Optional<Account> account = accountRepository.findByNumber(request.getAccountNumber());
        if (account.isEmpty()) {
            throw new RuntimeException("Account not found");
        } else if (Objects.equals(request.getTransactionType(), TransactionType.DEPOSIT)) {
            account.get().setBalance(account.get().getBalance().add(request.getAmount()));
        } else if (Objects.equals(request.getTransactionType(), TransactionType.WITHDRAW)) {
            account.get().setBalance(account.get().getBalance().subtract(request.getAmount()));
        }
        accountRepository.save(account.get());
        String cacheLastName = "session lastName:" + request.getPhoneNumber();
        String cacheValueLastName = redisTemplate.opsForValue().get(cacheLastName);
        if (cacheValueLastName == null) {
            throw new RuntimeException("Account last name not found");
        }
        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setLastName(cacheValueLastName);
        transaction.setNumberAccount(account.get().getNumber());
        transaction.setAmount(request.getAmount());
        transaction.setCurrency(account.get().getCurrency().toString());
        transaction.setTransactionType(request.getTransactionType());
        transaction.setTimestamp(LocalDateTime.now());
        return transaction;
    }

    @Override
    public List<Account> getAccountsByClientId(ClientIdRequest request) {
        List<Account> accounts = accountRepository.findByClientId(request.getClientId());
        if (accounts.isEmpty()) {
            throw new RuntimeException("Accounts not found");
        }
        return accounts;
    }

    @Override
    public AccountDetailsRequest getAccountDetails(String number) {
        Optional<Account> account = accountRepository.findByNumber(number);
        if (account.isEmpty()) {
            throw new RuntimeException("Account not found");
        }
        AccountDetailsRequest accountDetailsRequest = new AccountDetailsRequest();
        accountDetailsRequest.setNumber(account.get().getNumber());
        accountDetailsRequest.setCurrency(account.get().getCurrency());
        accountDetailsRequest.setBalance(account.get().getBalance());
        accountDetailsRequest.setCreatedAt(account.get().getCreatedAt());
        accountDetailsRequest.setUpdatedAt(account.get().getUpdatedAt());
        accountDetailsRequest.setEndAt(account.get().getEndAt());
        return accountDetailsRequest;
    }
}
