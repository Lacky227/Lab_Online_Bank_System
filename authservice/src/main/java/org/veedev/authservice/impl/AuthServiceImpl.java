package org.veedev.authservice.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.veedev.authservice.dto.AuthResponse;
import org.veedev.authservice.dto.LoginRequest;
import org.veedev.authservice.dto.RegisterRequest;
import org.veedev.authservice.model.Client;
import org.veedev.authservice.repository.ClientRepository;
import org.veedev.authservice.service.AuthService;
import org.veedev.authservice.utils.JwtUtils;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<String, String> redisTemplate;
    private final JwtUtils jwtUtils;
    @Override
    public ResponseEntity<String> register(RegisterRequest registerRequest) {
        if (clientRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        } else if (clientRepository.existsByPhoneNumber(registerRequest.getPhoneNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Phone number already exists");
        }
        Client client = new Client();
        client.setFirstName(registerRequest.getFirstName());
        client.setLastName(registerRequest.getLastName());
        client.setEmail(registerRequest.getEmail());
        client.setPhoneNumber(registerRequest.getPhoneNumber());
        client.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        clientRepository.save(client);
        return ResponseEntity.status(HttpStatus.CREATED).body("Client created");
    }

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        Optional<Client> client = clientRepository.findByPhoneNumber(loginRequest.getPhoneNumber());
        if (client.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found with this phone number: " + loginRequest.getPhoneNumber());
        } else if (!passwordEncoder.matches(loginRequest.getPassword(), client.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password");
        }
        redisTemplate.opsForValue().set("session id:" + client.get().getPhoneNumber(), client.get().getId().toString(), 24, TimeUnit.HOURS);
        redisTemplate.opsForValue().set("session firstName:" + client.get().getPhoneNumber(), client.get().getFirstName(), 24, TimeUnit.HOURS);
        redisTemplate.opsForValue().set("session lastName:" + client.get().getPhoneNumber(), client.get().getLastName(), 24, TimeUnit.HOURS);
        String token = jwtUtils.generationToken(loginRequest.getPhoneNumber(), client.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(new AuthResponse(token));
    }
}
