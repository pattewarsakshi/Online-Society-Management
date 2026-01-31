package com.society.management.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SocietyResponseDto {
    private Long societyId;
    private String societyName;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private LocalDateTime createdAt;
}
