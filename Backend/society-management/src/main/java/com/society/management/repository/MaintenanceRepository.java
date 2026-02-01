package com.society.management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.society.management.entity.Maintenance;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {

    List<Maintenance> findBySociety_SocietyId(Long societyId);

    List<Maintenance> findByProperty_PropertyIdIn(List<Long> propertyIds);

    Optional<Maintenance> findByProperty_PropertyIdAndPeriodMonthAndPeriodYear(
            Long propertyId,
            Integer periodMonth,
            Integer periodYear
    );
}

	
