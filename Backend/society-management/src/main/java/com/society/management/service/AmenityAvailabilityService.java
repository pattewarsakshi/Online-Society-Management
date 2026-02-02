package com.society.management.service;

import java.time.LocalDate;
import java.util.List;

import com.society.management.dto.TimeSlotDto;

public interface AmenityAvailabilityService {
	List<TimeSlotDto> getAvailableSlots(
            Long societyId,
            Long amenityId,
            LocalDate date
    );
}
