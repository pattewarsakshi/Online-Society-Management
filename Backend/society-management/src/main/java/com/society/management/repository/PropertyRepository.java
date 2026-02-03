package com.society.management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
	
	List<Property> findByOwner_UserIdAndStatusNot(
	        Long ownerUserId,
	        PropertyStatus status
	);
	
	@Query("""
			 SELECT
			   COUNT(p),
			   SUM(CASE WHEN p.tenant IS NOT NULL THEN 1 ELSE 0 END),
			   SUM(CASE WHEN p.tenant IS NULL THEN 1 ELSE 0 END)
			 FROM Property p
			 WHERE p.society.societyId = :societyId
			""")
			Object[] propertyOccupancy(@Param("societyId") Long societyId);
//=====================================================
	@Query("""
		    SELECT
		        p.propertyId,
		        p.flatNumber,
		        p.block,
		        o.fullName,
		        t.fullName
		    FROM Property p
		    JOIN p.owner o
		    LEFT JOIN p.tenant t
		    WHERE p.society.societyId = :societyId
		""")
		List<Object[]> getPropertyCards(@Param("societyId") Long societyId);




	
}
