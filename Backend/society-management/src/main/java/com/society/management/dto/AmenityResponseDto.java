package com.society.management.dto;

import java.time.LocalTime;

import com.society.management.enumtype.AmenityStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AmenityResponseDto {

	private Long amenityId;
    private String name;
    private String description;  
    private LocalTime startTime;
    private LocalTime endTime;
    private String status;

}