package com.society.management.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Builder
public class AmenityBookingResponseDto {

    private Long bookingId;

    private String amenityName;

    private LocalDate bookingDate;
    private LocalTime startTime;
    private LocalTime endTime;

    private String status;
}
