package com.society.management.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminDashboardDto {
	
	
	    // Users
	    private Long admins;
	    private Long owners;
	    private Long tenants;

	    // Properties
	    private Long totalProperties;
	    private Long occupiedProperties;
	    private Long vacantProperties;

	    // Maintenance
	    private BigDecimal maintenanceTotal;
	    private BigDecimal maintenancePaid;
	    private BigDecimal maintenancePending;

	    // Amenities
	    private Long activeBookings;
	    private Long completedBookings;
	    private Long cancelledBookings;
	}

