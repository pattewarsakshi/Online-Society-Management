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
public interface AmenityBookingRepository extends JpaRepository<AmenityBooking, Long> 
{
	//====================================================
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
//==========================================================
@Query("""
	    select ab from AmenityBooking ab
	    join fetch ab.amenity
	    where ab.society.societyId = :societyId
	      and ab.bookedBy.email = :email
	    order by ab.createdAt desc
	""")
	List<AmenityBooking> findMyBookingsWithAmenity(
	        @Param("societyId") Long societyId,
	        @Param("email") String email
	);
//==============================================================
List<AmenityBooking> findBySociety_SocietyIdOrderByBookingDateDescStartTimeDesc(
        Long societyId
);
//===============================================================

Optional<AmenityBooking> findByBookingIdAndSociety_SocietyId(
        Long bookingId,
        Long societyId
);
//========================================================
List<AmenityBooking> findByStatusAndBookingDateBefore(
        BookingStatus status,
        LocalDate date
);
//=========================================================
List<AmenityBooking> findByStatusAndBookingDateAndEndTimeBefore(
        BookingStatus status,
        LocalDate bookingDate,
        LocalTime endTime
);

}
