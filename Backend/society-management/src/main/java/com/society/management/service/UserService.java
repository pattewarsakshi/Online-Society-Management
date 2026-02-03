package com.society.management.service;


import com.society.management.dto.MeResponseDto;
import com.society.management.dto.UserRegisterRequestDto;
import com.society.management.entity.Society;
import com.society.management.entity.User;
import com.society.management.enumtype.Role;

public interface UserService {

    // Internal user creation (used by SocietyService)
    User createUserInternal(
            UserRegisterRequestDto request,
            Role role,
            Society society
    );

    // Admin / Super Admin password reset
    void resetPassword(Long userId, String newPassword);

    // Logged-in user profile
    MeResponseDto getCurrentUser();
}


