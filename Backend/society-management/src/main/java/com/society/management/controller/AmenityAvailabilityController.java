package com.society.management.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.society.management.dto.TimeSlotDto;
import com.society.management.service.AmenityAvailabilityService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/societies/{societyId}/amenities")
@RequiredArgsConstructor
public class AmenityAvailabilityController {

    private final AmenityAvailabilityService availabilityService;

    @GetMapping("/{amenityId}/availability")
    public List<TimeSlotDto> getAvailability(
            @PathVariable Long societyId,
            @PathVariable Long amenityId,
            @RequestParam LocalDate date
    ) {
        return availabilityService.getAvailableSlots(
                societyId,
                amenityId,
                date
        );
    }
}
