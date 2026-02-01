package com.society.management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.society.management.entity.Property;
import com.society.management.enumtype.PropertyStatus;

public interface PropertyRepository extends JpaRepository<Property, Long> {
	
	boolean existsBySociety_SocietyIdAndFlatNumber(Long societyId, String flatNumber);
	
	boolean existsBySociety_SocietyIdAndFlatNumberAndBlock(
	        Long societyId,
	        String flatNumber,
	        String block
	);

	List<Property> findBySociety_SocietyId(Long societyId);
	
	Optional<Property> findByPropertyIdAndSociety_SocietyId(
	        Long propertyId,
	        Long societyId
	);
	
	List<Property> findBySociety_SocietyIdAndStatusNot(
	        Long societyId,
	        PropertyStatus status
	);


	
}
