package com.tej0897.msauth.service;

import com.tej0897.msauth.dto.AuthResponse;
import com.tej0897.msauth.dto.LoginRequest;
import com.tej0897.msauth.dto.SignupRequest;
import com.tej0897.msauth.entity.User;
import com.tej0897.msauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public AuthResponse register(SignupRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            log.error("{} username already exists", request.getUsername());
            throw new IllegalArgumentException("Username is already taken.");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            log.error("{} email address is already taken", request.getEmail());
            throw new IllegalArgumentException("Email is already registered.");
        }

        User user = User.builder().username(request.getUsername()).email(request.getEmail()).passwordHash(passwordEncoder.encode(request.getPassword())).createdAt(LocalDateTime.now()).build();

        userRepository.save(user);

        return new AuthResponse("User registered successfully.");
    }


    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new IllegalArgumentException("Invalid Credentials."));
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid Credentials.");
        }
        return new AuthResponse("Login successful.");
    }
}
