package com.society.management.repository;



import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.society.management.entity.Amenity;
import com.society.management.entity.AmenityBooking;
import com.society.management.entity.Society;
import com.society.management.enumtype.BookingStatus;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {



Optional<Amenity> findByAmenityIdAndSociety_SocietyId(
        Long amenityId,
        Long societyId
);

boolean existsBySociety_SocietyIdAndNameIgnoreCase(
        Long societyId,
        String name
);

List<Amenity> findBySociety(Society society);


}
