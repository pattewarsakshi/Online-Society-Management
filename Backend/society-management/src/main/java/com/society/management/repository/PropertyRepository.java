package com.society.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.society.management.entity.Property;

public interface PropertyRepository extends JpaRepository<Property, Long> {
	
	boolean existsBySociety_SocietyIdAndFlatNumber(Long societyId, String flatNumber);
	
	boolean existsBySociety_SocietyIdAndFlatNumberAndBlock(
	        Long societyId,
	        String flatNumber,
	        String block
	);

	List<Property> findBySociety_SocietyId(Long societyId);

	
}
