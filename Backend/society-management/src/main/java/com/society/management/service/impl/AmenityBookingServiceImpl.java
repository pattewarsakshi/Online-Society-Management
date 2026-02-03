package com.society.management.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.society.management.dto.AmenityBookingResponseDto;
import com.society.management.dto.CreateAmenityBookingDto;
import com.society.management.entity.Amenity;
import com.society.management.entity.AmenityBooking;
import com.society.management.entity.User;
import com.society.management.enumtype.AmenityStatus;
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
    
    //==================================================================

    @Override
    @Transactional
    public AmenityBookingResponseDto createBooking(
            Long societyId,
            CreateAmenityBookingDto dto,
            String userEmail
    ) {

        // 1️⃣ Validate time & date (fast fail)
        validateBookingTime(
                dto.getBookingDate(),
                dto.getStartTime(),
                dto.getEndTime()
        );

        // 2️⃣ Load user
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"
                ));

        // 3️⃣ Load amenity
        Amenity amenity = amenityRepository.findById(dto.getAmenityId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Amenity not found"
                ));

        // 4️⃣ Society ownership check
        if (!amenity.getSociety().getSocietyId().equals(societyId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Amenity does not belong to this society"
            );
        }

        // 5️⃣ Amenity must be ACTIVE
        if (amenity.getStatus() != AmenityStatus.ACTIVE) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Amenity is inactive"
            );
        }

        // 6️⃣ Overlap check (single source of truth)
        boolean hasConflict =
                !bookingRepository.findConflictingBookings(
                        amenity.getAmenityId(),
                        dto.getBookingDate(),
                        dto.getStartTime(),
                        dto.getEndTime()
                ).isEmpty();

        if (hasConflict) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Amenity already booked for this time slot"
            );
        }

        // 7️⃣ Create & save booking
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

        AmenityBooking saved = bookingRepository.save(booking);

        // 8️⃣ Map to response DTO
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
        return
        		bookingRepository.findMyBookingsWithAmenity(societyId, userEmail)
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
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Only CREATED bookings can be cancelled"
            );
        }

        // 4️⃣ Soft cancel
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }
    
    //=====================================================
    private void validateBookingTime(
            LocalDate bookingDate,
            LocalTime startTime,
            LocalTime endTime
    ) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        // 1️⃣ Past date not allowed
        if (bookingDate.isBefore(today)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cannot book for a past date"
            );
        }

        // 2️⃣ Start time must be before end time
        if (!startTime.isBefore(endTime)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Start time must be before end time"
            );
        }

        // 3️⃣ Today: past time not allowed
        if (bookingDate.isEqual(today) && !startTime.isAfter(now)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cannot book a past time slot"
            );
        }
    }

    //============================================================
    @Override
    @Transactional
    public void completeExpiredBookings() {

        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        // 1️⃣ Complete past-date bookings
        List<AmenityBooking> pastBookings =
                bookingRepository.findByStatusAndBookingDateBefore(
                        BookingStatus.CREATED,
                        today
                );

        for (AmenityBooking booking : pastBookings) {
            booking.setStatus(BookingStatus.COMPLETED);
        }

        // 2️⃣ Complete today's expired bookings
        List<AmenityBooking> todayExpired =
                bookingRepository.findByStatusAndBookingDateAndEndTimeBefore(
                        BookingStatus.CREATED,
                        today,
                        now
                );

        for (AmenityBooking booking : todayExpired) {
            booking.setStatus(BookingStatus.COMPLETED);
        }
    }

    //==============================================

}
