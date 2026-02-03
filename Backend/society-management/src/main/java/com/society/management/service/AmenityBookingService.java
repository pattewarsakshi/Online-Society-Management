package com.society.management.service;

import java.util.List;

import com.society.management.dto.AmenityBookingResponseDto;
import com.society.management.dto.CreateAmenityBookingDto;

public interface AmenityBookingService {

    AmenityBookingResponseDto createBooking(
            Long societyId,
            CreateAmenityBookingDto dto,
            String userEmail
    );
    
    List<AmenityBookingResponseDto> getMyBookings(
            Long societyId,
            String userEmail
    );
    
    List<AmenityBookingResponseDto> getAllBookingsBySociety(Long societyId);
    
    void cancelBooking(
            Long societyId,
            Long bookingId,
            String userEmail
    );
    
   
     
    void completeExpiredBookings();


}
