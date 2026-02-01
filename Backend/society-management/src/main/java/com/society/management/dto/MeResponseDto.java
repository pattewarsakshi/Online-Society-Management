package com.society.management.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MeResponseDto {

    private Long userId;
    private String fullName;
    private String email;
    private String phone;
    private String role;
    private Long societyId;
    private String societyName;
}