package com.society.management.service;

import com.society.management.dto.AuthResponse;
import com.society.management.dto.LoginRequestDto;

public interface AuthService {
    AuthResponse login(LoginRequestDto loginRequest);
}