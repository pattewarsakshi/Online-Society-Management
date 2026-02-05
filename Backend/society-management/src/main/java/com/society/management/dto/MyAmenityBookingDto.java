package com.society.management.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyAmenityBookingDto {

    private Long bookingId;
    private String amenityName;

    private LocalDate bookingDate;
    private LocalTime startTime;
    private LocalTime endTime;

    private String status;
}
