package com.society.management.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.society.management.dto.AmenityBookingResponseDto;
import com.society.management.dto.CreateAmenityBookingDto;
import com.society.management.entity.Amenity;
import com.society.management.entity.AmenityBooking;
import com.society.management.entity.User;
import com.society.management.enumtype.BookingStatus;
import com.society.management.repository.AmenityBookingRepository;
import com.society.management.repository.AmenityRepository;
import com.society.management.repository.UserRepository;
import com.society.management.service.AmenityBookingService;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AmenityBookingServiceImpl implements AmenityBookingService {

    private final AmenityRepository amenityRepository;
    private final AmenityBookingRepository bookingRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public AmenityBookingResponseDto createBooking(
            Long societyId,
            CreateAmenityBookingDto dto,
            String userEmail
    ) {

        // 1️⃣ User must exist
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2️⃣ Amenity must exist
        Amenity amenity = amenityRepository.findById(dto.getAmenityId())
                .orElseThrow(() -> new RuntimeException("Amenity not found"));

        // 3️⃣ Amenity must belong to same society
        if (!amenity.getSociety().getSocietyId().equals(societyId)) {
            throw new RuntimeException("Amenity does not belong to this society");
        }

        // 4️⃣ Amenity must be active
        if (!amenity.isActive()) {
            throw new RuntimeException("Amenity is not active");
        }

        // 5️⃣ Check time-slot conflicts
        List<AmenityBooking> conflicts =
                bookingRepository.findConflictingBookings(
                        dto.getAmenityId(),
                        dto.getBookingDate(),
                        dto.getStartTime(),
                        dto.getEndTime()
                );

        if (!conflicts.isEmpty()) {
            throw new RuntimeException("Time slot already booked");
        }

        // 6️⃣ Create booking
        AmenityBooking booking = AmenityBooking.builder()
                .amenity(amenity)
                .society(amenity.getSociety())
                .bookedBy(user)
                .bookingDate(dto.getBookingDate())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .status(BookingStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .build();
        
        boolean conflict =
                bookingRepository.existsByAmenity_AmenityIdAndBookingDateAndStatusAndStartTimeLessThanAndEndTimeGreaterThan(
                        amenity.getAmenityId(),
                        dto.getBookingDate(),
                        BookingStatus.BOOKED,
                        dto.getEndTime(),
                        dto.getStartTime()
                );

        if (conflict) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Amenity already booked for this time slot"
            );
        }


        AmenityBooking saved = bookingRepository.save(booking);

        // 7️⃣ Map to response
        return AmenityBookingResponseDto.builder()
                .bookingId(saved.getBookingId())
                .amenityName(amenity.getName())
                .bookingDate(saved.getBookingDate())
                .startTime(saved.getStartTime())
                .endTime(saved.getEndTime())
                .status(saved.getStatus().name())
                .build();
    }
    //==================================================================
    @Override
    public List<AmenityBookingResponseDto> getMyBookings(
            Long societyId,
            String userEmail
    ) {
        return bookingRepository
                .findBySociety_SocietyIdAndBookedBy_EmailOrderByCreatedAtDesc(
                        societyId,
                        userEmail
                )
                .stream()
                .map(booking -> AmenityBookingResponseDto.builder()
                        .bookingId(booking.getBookingId())
                        .amenityName(booking.getAmenity().getName())
                        .bookingDate(booking.getBookingDate())
                        .startTime(booking.getStartTime())
                        .endTime(booking.getEndTime())
                        .status(booking.getStatus().name())
                        .build()
                )
                .toList();
    }
    
    //================================================================
    @Override
    public List<AmenityBookingResponseDto> getAllBookingsBySociety(Long societyId) {

        return bookingRepository
                .findBySociety_SocietyIdOrderByBookingDateDescStartTimeDesc(societyId)
                .stream()
                .map(booking -> AmenityBookingResponseDto.builder()
                        .bookingId(booking.getBookingId())
                        .amenityName(booking.getAmenity().getName())
                        .bookingDate(booking.getBookingDate())
                        .startTime(booking.getStartTime())
                        .endTime(booking.getEndTime())
                        .status(booking.getStatus().name())
                        .build()
                )
                .toList();
    }
//================================================================
    @Override
    @Transactional
    public void cancelBooking(
            Long societyId,
            Long bookingId,
            String userEmail
    ) {
        // 1️⃣ Booking must exist & belong to society
        AmenityBooking booking = bookingRepository
                .findByBookingIdAndSociety_SocietyId(bookingId, societyId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // 2️⃣ Only creator can cancel
        if (!booking.getBookedBy().getEmail().equals(userEmail)) {
            throw new RuntimeException("You can cancel only your own booking");
        }

        // 3️⃣ Only CREATED bookings can be cancelled
        if (booking.getStatus() != BookingStatus.CREATED) {
            throw new RuntimeException("Booking already cancelled");
        }

        // 4️⃣ Soft cancel
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }
    
    //=====================================================
    @Override
    @Transactional
    public void cancelBooking(Long bookingId, Long userId) {

        AmenityBooking booking = bookingRepository
                .findByBookingIdAndBookedBy_UserId(bookingId, userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Booking not found"
                ));

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Booking already cancelled"
            );
        }

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }



}
