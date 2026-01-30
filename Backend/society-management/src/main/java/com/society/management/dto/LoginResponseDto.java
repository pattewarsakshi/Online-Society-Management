package com.society.management.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * DTO returned after successful login.
 */
@Getter
@Builder
public class LoginResponseDto {

    private String token;
    private String role;
    private Long societyId;
}

