package com.society.management.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.society.management.service.AmenityBookingService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookingStatusScheduler {

    private final AmenityBookingService amenityBookingService;

    @Scheduled(fixedRate = 60000)
    public void autoCompleteBookings() {
        amenityBookingService.completeExpiredBookings();
    }

}