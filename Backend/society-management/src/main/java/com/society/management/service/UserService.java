package com.society.management.service;

import com.society.management.dto.MeResponseDto;
import com.society.management.dto.UserRegisterRequestDto;
import com.society.management.dto.UserResponseDto;

public interface UserService {

    UserResponseDto registerUser(UserRegisterRequestDto requestDto);
    
    void resetPassword(Long userId, String newPassword);
    
    MeResponseDto getCurrentUser();
}
