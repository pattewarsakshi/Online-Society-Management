package com.society.management.service;

import java.util.List;

import com.society.management.dto.CreateMaintenanceRequestDto;
import com.society.management.dto.MaintenanceResponseDto;

public interface MaintenanceService {

    MaintenanceResponseDto createMaintenance(
            Long propertyId,
            CreateMaintenanceRequestDto dto
    );

    List<MaintenanceResponseDto> getBySociety(Long societyId);

    void markAsPaid(Long maintenanceId);
}
