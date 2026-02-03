package com.society.management.service.impl;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.society.management.dto.TimeSlotDto;
import com.society.management.entity.Amenity;
import com.society.management.entity.AmenityBooking;
import com.society.management.enumtype.BookingStatus;
import com.society.management.repository.AmenityBookingRepository;
import com.society.management.repository.AmenityRepository;
import com.society.management.service.AmenityAvailabilityService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AmenityAvailabilityServiceImpl
        implements AmenityAvailabilityService {

	private final AmenityBookingRepository amenityBookingRepository;
    private final AmenityRepository amenityRepository;
    @Override
    public List<TimeSlotDto> getAvailableSlots(
            Long societyId,
            Long amenityId,
            LocalDate date) {

        // 1️⃣ Validate amenity belongs to society
        Amenity amenity = amenityRepository
                .findByAmenityIdAndSociety_SocietyId(amenityId, societyId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Amenity not found"
                ));

        // 2️⃣ Fetch ACTIVE bookings (CREATED)
        List<AmenityBooking> bookings =
                amenityBookingRepository.findConflictingBookings(
                        amenityId,
                        date,
                        LocalTime.MIN,
                        LocalTime.MAX
                );

        // 3️⃣ Sort by start time
        bookings.sort(Comparator.comparing(AmenityBooking::getStartTime));

        List<TimeSlotDto> available = new ArrayList<>();

        LocalTime current = LocalTime.of(6, 0);
        LocalTime closing = LocalTime.of(23, 0);

        for (AmenityBooking b : bookings) {
            if (current.isBefore(b.getStartTime())) {
                available.add(new TimeSlotDto(
                        current,
                        b.getStartTime()
                ));
            }
            current = b.getEndTime();
        }

        if (current.isBefore(closing)) {
            available.add(new TimeSlotDto(current, closing));
        }

        return available;
    }

}

