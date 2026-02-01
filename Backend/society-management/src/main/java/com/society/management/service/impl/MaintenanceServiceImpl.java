package com.society.management.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.society.management.dto.CreateMaintenanceRequestDto;
import com.society.management.dto.MaintenanceResponseDto;
import com.society.management.entity.Maintenance;
import com.society.management.entity.Property;
import com.society.management.enumtype.MaintenanceStatus;
import com.society.management.repository.MaintenanceRepository;
import com.society.management.repository.PropertyRepository;
import com.society.management.service.MaintenanceService;
import com.society.management.exception.GlobalExceptionHandler;
import com.society.management.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class MaintenanceServiceImpl implements MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final PropertyRepository propertyRepository;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public MaintenanceResponseDto createMaintenance(
            Long propertyId,
            CreateMaintenanceRequestDto dto
    ) {
    	Property property = propertyRepository.findById(propertyId)
    	        .orElseThrow(() ->
    	                new ResourceNotFoundException("Property not found"));


        Maintenance maintenance = Maintenance.builder()
            .property(property)
            .amount(dto.getAmount())
            .dueDate(dto.getDueDate())
            .status(MaintenanceStatus.PENDING)
            .createdAt(LocalDateTime.now())
            .build();

        Maintenance saved = maintenanceRepository.save(maintenance);
        return mapToDto(saved);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<MaintenanceResponseDto> getBySociety(Long societyId) {
        return maintenanceRepository
                .findByProperty_Society_SocietyId(societyId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void markAsPaid(Long maintenanceId) {
        Maintenance maintenance = maintenanceRepository.findById(maintenanceId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Maintenance not found"));

        maintenance.setStatus(MaintenanceStatus.PAID);
    }

    private MaintenanceResponseDto mapToDto(Maintenance m) {
        return new MaintenanceResponseDto(
                m.getMaintenanceId(),
                m.getProperty().getPropertyId(),
                m.getAmount(),
                m.getDueDate(),
                m.getStatus()
        );
    }
}

