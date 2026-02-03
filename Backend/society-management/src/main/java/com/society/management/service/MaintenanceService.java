package com.society.management.service;

import java.util.List;

import com.society.management.dto.CreateMaintenanceRequestDto;
import com.society.management.dto.MaintenanceResponseDto;
import com.society.management.dto.MaintenanceSummaryDto;

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
    
    MaintenanceSummaryDto getMaintenanceSummary(Long societyId);
    
    List<MaintenanceResponseDto> getMyMaintenance(String tenantEmail);


}