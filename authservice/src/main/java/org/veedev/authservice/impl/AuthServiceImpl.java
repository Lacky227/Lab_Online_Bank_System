package org.veedev.authservice.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.veedev.authservice.dto.LoginRequest;
import org.veedev.authservice.dto.RegisterRequest;
import org.veedev.authservice.model.Client;
import org.veedev.authservice.repository.AuthRepository;
import org.veedev.authservice.service.AuthService;

import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, String> redisTemplate;
    @Override
    public ResponseEntity<String> register(RegisterRequest registerRequest) {
        if (authRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        } else if (authRepository.existsByPhoneNumber(registerRequest.getPhoneNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Phone number already exists");
        }
        Client client = new Client();
        client.setFirstName(registerRequest.getFirstName());
        client.setLastName(registerRequest.getLastName());
        client.setEmail(registerRequest.getEmail());
        client.setPhoneNumber(registerRequest.getPhoneNumber());
        client.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        authRepository.save(client);
        return ResponseEntity.status(HttpStatus.CREATED).body("Client created");
    }

    @Override
    public ResponseEntity<String> login(LoginRequest loginRequest) {
        Client client = authRepository.findByPhoneNumber(loginRequest.getPhoneNumber());
        if (client == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found with this phone number: " + loginRequest.getPhoneNumber());
        } else if (!passwordEncoder.matches(loginRequest.getPassword(), client.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password");
        }
        redisTemplate.opsForValue().set("session:" + client.getPhoneNumber(), client.getId().toString(), 24, TimeUnit.HOURS);
        redisTemplate.opsForValue().set("session:" + client.getPhoneNumber(), client.getFirstName(), 24, TimeUnit.HOURS);
        redisTemplate.opsForValue().set("session:" + client.getPhoneNumber(), client.getLastName(), 24, TimeUnit.HOURS);
        return ResponseEntity.status(HttpStatus.OK).body("Login successful");
    }
}
