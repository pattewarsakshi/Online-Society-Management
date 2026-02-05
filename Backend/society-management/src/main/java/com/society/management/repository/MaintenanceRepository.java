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
    
    List<Maintenance> findByProperty_Tenant_UserId(Long tenantUserId);
//=================================================================
    
    @Query("""
    	    SELECT
    	        COALESCE(SUM(m.amount), 0),
    	        COALESCE(SUM(CASE WHEN m.status = 'PAID' THEN m.amount ELSE 0 END), 0),
    	        COALESCE(SUM(CASE WHEN m.status = 'PENDING' THEN m.amount ELSE 0 END), 0),
    	        COUNT(m)
    	    FROM Maintenance m
    	    JOIN m.property p
    	    WHERE p.tenant.userId = :tenantId
    	""")
    	Object[] getTenantDashboardSummary(@Param("tenantId") Long tenantId);
//=========================================================
    @Query("""
    	    SELECT
    	        COALESCE(SUM(m.amount), 0),
    	        COALESCE(SUM(CASE WHEN m.status = 'PAID' THEN m.amount ELSE 0 END), 0),
    	        COALESCE(SUM(CASE WHEN m.status = 'PENDING' THEN m.amount ELSE 0 END), 0),
    	        COUNT(m)
    	    FROM Maintenance m
    	    JOIN m.property p
    	    WHERE p.owner.userId = :ownerId
    	""")
    	Object[] getOwnerDashboardSummary(@Param("ownerId") Long ownerId);
//==========================================
    @Query("""
    	    SELECT
    	        p.propertyId,
    	        p.flatNumber,
    	        p.block,
    	        COALESCE(SUM(m.amount), 0),
    	        COALESCE(SUM(CASE WHEN m.status = 'PAID' THEN m.amount ELSE 0 END), 0),
    	        COALESCE(SUM(CASE WHEN m.status = 'PENDING' THEN m.amount ELSE 0 END), 0),
    	        COUNT(m)
    	    FROM Maintenance m
    	    JOIN m.property p
    	    WHERE p.owner.userId = :ownerId
    	    GROUP BY p.propertyId, p.flatNumber, p.block
    	""")
    List<Object[]> getOwnerPropertyWiseSummary(@Param("ownerId") Long ownerId);
//====================================================================
    @Query("""
    		 SELECT
    		   COALESCE(SUM(m.amount),0),
    		   COALESCE(SUM(CASE WHEN m.status='PAID' THEN m.amount ELSE 0 END),0),
    		   COALESCE(SUM(CASE WHEN m.status='PENDING' THEN m.amount ELSE 0 END),0)
    		 FROM Maintenance m
    		 WHERE m.property.society.societyId = :societyId
    		""")
    		Object[] maintenanceTotals(@Param("societyId") Long societyId);
//==================================================================
    @Query("""
    	    SELECT m
    	    FROM Maintenance m
    	    JOIN m.property p
    	    WHERE p.tenant.email = :email
    	    ORDER BY m.dueDate DESC
    	""")
    	List<Maintenance> findTenantMaintenance(@Param("email") String email);

}

	
