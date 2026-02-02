package com.society.management.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.society.management.entity.Maintenance;
import com.society.management.enumtype.MaintenanceStatus;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {

    List<Maintenance> findBySociety_SocietyId(Long societyId);

    List<Maintenance> findByProperty_PropertyIdIn(List<Long> propertyIds);

    Optional<Maintenance> findByProperty_PropertyIdAndPeriodMonthAndPeriodYear(
            Long propertyId,
            Integer periodMonth,
            Integer periodYear
    );
    
    List<Maintenance> findByStatusAndDueDateBefore(
            MaintenanceStatus status,
            LocalDate date
    );
    
    long countBySociety_SocietyId(Long societyId);

    long countBySociety_SocietyIdAndStatus(
            Long societyId,
            MaintenanceStatus status
    );

    @Query("""
        SELECT COALESCE(SUM(m.amount), 0)
        FROM Maintenance m
        WHERE m.society.societyId = :societyId
          AND m.status = :status
    """)
    BigDecimal sumAmountBySocietyAndStatus(
            @Param("societyId") Long societyId,
            @Param("status") MaintenanceStatus status
    );

}

	
