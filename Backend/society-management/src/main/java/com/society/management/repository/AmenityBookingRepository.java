package com.society.management.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.society.management.entity.AmenityBooking;
import com.society.management.enumtype.BookingStatus;
public interface AmenityBookingRepository
extends JpaRepository<AmenityBooking, Long> {

@Query("""
SELECT b FROM AmenityBooking b
WHERE b.amenity.amenityId = :amenityId
  AND b.bookingDate = :date
  AND b.status = 'CREATED'
  AND (
      :start < b.endTime AND :end > b.startTime
  )
""")
List<AmenityBooking> findConflictingBookings(
    @Param("amenityId") Long amenityId,
    @Param("date") LocalDate date,
    @Param("start") LocalTime start,
    @Param("end") LocalTime end
);

List<AmenityBooking> findBySociety_SocietyIdAndBookedBy_EmailOrderByCreatedAtDesc(
        Long societyId,
        String email
);

List<AmenityBooking> findBySociety_SocietyIdOrderByBookingDateDescStartTimeDesc(
        Long societyId
);

Optional<AmenityBooking> findByBookingIdAndSociety_SocietyId(
        Long bookingId,
        Long societyId
);
List<AmenityBooking> findByAmenity_AmenityIdAndBookingDateAndStatus(
        Long amenityId,
        LocalDate bookingDate,
        BookingStatus status
);
Optional<AmenityBooking> findByBookingIdAndBookedBy_UserId(
	    Long bookingId,
	    Long userId
	);
boolean existsByAmenity_AmenityIdAndBookingDateAndStatusAndStartTimeLessThanAndEndTimeGreaterThan(
        Long amenityId,
        LocalDate bookingDate,
        BookingStatus status,
        LocalTime endTime,
        LocalTime startTime
);
List<AmenityBooking> findByStatusAndEndTimeBefore(BookingStatus booked, LocalTime now);

}
