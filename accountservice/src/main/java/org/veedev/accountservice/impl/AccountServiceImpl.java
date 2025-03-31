package org.veedev.accountservice.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.veedev.accountservice.dto.CreateAccountRequest;
import org.veedev.accountservice.model.Account;
import org.veedev.accountservice.repository.AccountRepository;
import org.veedev.accountservice.service.AccountService;
import org.veedev.accountservice.util.AccountUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, String> redisTemplate;
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
        redisTemplate.opsForValue().set("session lastName:" + account.getId(), clientId.toString(), 24, TimeUnit.HOURS);
        return ResponseEntity.status(HttpStatus.CREATED).body("Account created successfully, your account number is: " + account.getNumber() + ", pin code is: " + pinCode + ", and account end is: " + account.getEndAt());
    }
}
