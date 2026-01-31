package com.society.management.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminResponseDto {

    private Long userId;
    private String fullName;
    private String email;
    private String role;
    private Long societyId;
}
