package com.society.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.society.management.entity.Property;

public interface PropertyRepository extends JpaRepository<Property, Long> {
	
	boolean existsBySociety_SocietyIdAndFlatNumber(Long societyId, String flatNumber);
	
}
