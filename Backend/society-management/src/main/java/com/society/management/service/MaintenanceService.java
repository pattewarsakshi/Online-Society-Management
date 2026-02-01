package com.society.management.service;

import java.util.List;

import com.society.management.dto.CreateMaintenanceRequestDto;
import com.society.management.dto.MaintenanceResponseDto;

public interface MaintenanceService {

    // ADMIN
    MaintenanceResponseDto createMaintenanceForSociety(
            Long societyId,
            CreateMaintenanceRequestDto dto
    );

    List<MaintenanceResponseDto> getBySociety(Long societyId);

    // OWNER
    List<MaintenanceResponseDto> getMaintenanceForOwner(
            Long societyId,
            String ownerEmail
    );

    void markAsPaid(Long societyId, Long maintenanceId);
}