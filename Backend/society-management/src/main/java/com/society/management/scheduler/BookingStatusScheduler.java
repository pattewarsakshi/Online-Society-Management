package com.society.management.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.society.management.service.AmenityBookingService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookingStatusScheduler {

    private final AmenityBookingService bookingService;

    @Scheduled(fixedRate = 60000) // every 1 minute
    public void autoCompleteBookings() {
        bookingService.completeExpiredBookings();
    }
}