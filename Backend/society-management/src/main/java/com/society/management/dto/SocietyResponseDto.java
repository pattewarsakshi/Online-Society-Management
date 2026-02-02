package com.society.management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocietyResponseDto {
    private Long societyId;
    private String societyName;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private LocalDateTime createdAt;
}
