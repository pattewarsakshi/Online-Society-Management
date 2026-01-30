package com.society.management.service;

import com.society.management.dto.LoginRequestDto;
import com.society.management.dto.LoginResponseDto;

public interface AuthService {

    LoginResponseDto login(LoginRequestDto requestDto);
}
