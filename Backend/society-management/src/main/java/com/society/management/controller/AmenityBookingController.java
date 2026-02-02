package com.society.management.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.society.management.dto.AmenityBookingResponseDto;
import com.society.management.dto.CreateAmenityBookingDto;
import com.society.management.service.AmenityBookingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/societies/{societyId}/amenities")
@RequiredArgsConstructor
public class AmenityBookingController {

    private final AmenityBookingService bookingService;

    @PostMapping("/bookings")
    @PreAuthorize("hasAnyRole('OWNER','TENANT')")
    public AmenityBookingResponseDto bookAmenity(
            @PathVariable Long societyId,
            @RequestBody @Valid CreateAmenityBookingDto dto,
            Authentication authentication
    ) {
        return bookingService.createBooking(
                societyId,
                dto,
                authentication.getName()
        );
    }
    //=========================================================
    @GetMapping("/bookings/my")
    @PreAuthorize("hasAnyRole('OWNER','TENANT')")
    public List<AmenityBookingResponseDto> getMyBookings(
            @PathVariable Long societyId,
            Authentication authentication
    ) {
        return bookingService.getMyBookings(
                societyId,
                authentication.getName()
        );
    }
    
    //============================================================
    @GetMapping("/bookings")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public List<AmenityBookingResponseDto> getAllBookings(
            @PathVariable Long societyId
    ) {
        return bookingService.getAllBookingsBySociety(societyId);
    }
    
    //==============================================================
    @PutMapping("/bookings/{bookingId}/cancel")
    @PreAuthorize("hasAnyRole('OWNER','TENANT')")
    public ResponseEntity<String> cancelBooking(
            @PathVariable Long societyId,
            @PathVariable Long bookingId,
            Authentication authentication
    ) {
        bookingService.cancelBooking(
                societyId,
                bookingId,
                authentication.getName()
        );
        return ResponseEntity.ok("Booking cancelled successfully");
    }



}
