package com.society.management.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.society.management.dto.MyAmenityBookingDto;
import com.society.management.service.AmenityBookingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/amenities")
@RequiredArgsConstructor
public class MyAmenityBookingController {

    private final AmenityBookingService bookingService;

    @GetMapping("/my-bookings")
    @PreAuthorize("hasAnyRole('TENANT','OWNER')")
    public ResponseEntity<List<MyAmenityBookingDto>> getMyBookings() {

        return ResponseEntity.ok(
                bookingService.getMyBookings()
        );
    }
}

