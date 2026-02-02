package com.society.management.dto;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TimeSlotDto {
    private LocalTime startTime;
    private LocalTime endTime;
}