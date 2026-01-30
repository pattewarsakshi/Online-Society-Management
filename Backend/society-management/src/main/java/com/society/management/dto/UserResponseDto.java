package com.society.management.dto;

import com.society.management.enumtype.Role;
import lombok.Builder;
import lombok.Getter;

/**
 * DTO returned after successful user registration.
 */
@Getter
@Builder
public class UserResponseDto {

    private Long userId;
    private String fullName;
    private String email;
    private String phone;
    private Role role;
    private Long societyId;
}
