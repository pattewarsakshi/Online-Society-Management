package com.society.management.service;

import com.society.management.dto.LoginRequestDto;
import com.society.management.dto.LoginResponseDto;
import com.society.management.dto.RegisterSuperAdminRequest;

public interface AuthService {
    void registerSuperAdmin(RegisterSuperAdminRequest request);
    LoginResponseDto login(LoginRequestDto request);

}
