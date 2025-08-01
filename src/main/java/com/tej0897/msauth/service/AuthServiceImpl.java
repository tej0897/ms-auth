package com.tej0897.msauth.service;

import com.tej0897.msauth.dto.AuthResponse;
import com.tej0897.msauth.dto.LoginRequest;
import com.tej0897.msauth.dto.SignupRequest;
import com.tej0897.msauth.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final FirestoreService firestoreService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public AuthResponse register(SignupRequest request) {

        try {
            if (firestoreService.existsByUsername(request.getUsername())) {
                log.error("{} : username is already taken", request.getUsername());
                throw new IllegalArgumentException("Username already exists");
            }

            if (firestoreService.existsByEmail(request.getEmail())) {
                log.error("{} : Email ID is already taken", request.getEmail());
                throw new IllegalArgumentException("Email already exists");
            }

            User user = User.builder().username(request.getUsername()).email(request.getEmail()).passwordHash(passwordEncoder.encode(request.getPassword())).build();
            String updatedTime = firestoreService.saveUser(user);
            log.info("saved user at {} ", updatedTime);
            return new AuthResponse("User registered successfully.");
        } catch (Exception e) {
            throw new RuntimeException("Registration failed: " + e.getMessage());
        }
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        try {
            User user = firestoreService.getUserByUsername(request.getUsername());
            if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
                throw new IllegalArgumentException("Invalid credentials.");
            }

            return new AuthResponse("Login successful (JWT coming soon).");
        } catch (Exception e) {
            throw new RuntimeException("Login failed: " + e.getMessage());
        }
    }
}
