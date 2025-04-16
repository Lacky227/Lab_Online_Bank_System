package org.veedev.authservice.service;

import org.springframework.http.ResponseEntity;
import org.veedev.authservice.dto.LoginRequest;
import org.veedev.authservice.dto.RegisterRequest;

public interface AuthService {
    ResponseEntity<String> register(RegisterRequest registerRequest);
    ResponseEntity<?> login(LoginRequest loginRequest);
}
