package com.society.management.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.society.management.dto.AdminDashboardDto;
import com.society.management.repository.AmenityBookingRepository;
import com.society.management.repository.MaintenanceRepository;
import com.society.management.repository.PropertyRepository;
import com.society.management.repository.UserRepository;
import com.society.management.service.AdminDashboardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminDashboardServiceImpl implements AdminDashboardService {

    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final MaintenanceRepository maintenanceRepository;
    private final AmenityBookingRepository amenityBookingRepository;
    @Override
    public AdminDashboardDto getDashboard(Long societyId) {

        Object[] users = unwrap(
                userRepository.countUsersByRole(societyId)
        );

        Object[] properties = unwrap(
                propertyRepository.propertyOccupancy(societyId)
        );

        Object[] maintenance = unwrap(
                maintenanceRepository.maintenanceTotals(societyId)
        );

        Object[] bookings = unwrap(
                amenityBookingRepository.bookingCounts(societyId)
        );

        return AdminDashboardDto.builder()

                // Users
                .admins(((Number) users[0]).longValue())
                .owners(((Number) users[1]).longValue())
                .tenants(((Number) users[2]).longValue())

                // Properties
                .totalProperties(((Number) properties[0]).longValue())
                .occupiedProperties(((Number) properties[1]).longValue())
                .vacantProperties(((Number) properties[2]).longValue())

                // Maintenance
                .maintenanceTotal((java.math.BigDecimal) maintenance[0])
                .maintenancePaid((java.math.BigDecimal) maintenance[1])
                .maintenancePending((java.math.BigDecimal) maintenance[2])

                // Amenity bookings
                .activeBookings(((Number) bookings[0]).longValue())
                .completedBookings(((Number) bookings[1]).longValue())
                .cancelledBookings(((Number) bookings[2]).longValue())

                .build();
    }

 
    
    private Object[] unwrap(Object result) {

        if (result instanceof Object[]
                && ((Object[]) result).length == 1
                && ((Object[]) result)[0] instanceof Object[]) {

            return (Object[]) ((Object[]) result)[0];
        }

        return (Object[]) result;
    }
}



