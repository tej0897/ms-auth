package com.tej0897.msauth.service;

import com.tej0897.msauth.dto.AuthResponse;
import com.tej0897.msauth.dto.LoginRequest;
import com.tej0897.msauth.dto.SignupRequest;

public interface AuthService {
    AuthResponse register(SignupRequest request);

    AuthResponse login(LoginRequest request);
}
