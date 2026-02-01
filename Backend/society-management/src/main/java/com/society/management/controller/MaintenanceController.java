package com.society.management.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.society.management.dto.CreateMaintenanceRequestDto;
import com.society.management.dto.MaintenanceResponseDto;
import com.society.management.security.CustomUserDetails;
import com.society.management.service.MaintenanceService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    @PostMapping("/properties/{propertyId}/maintenance")
    public MaintenanceResponseDto create(
            @PathVariable Long propertyId,
            @RequestBody CreateMaintenanceRequestDto dto
    ) {
        return maintenanceService.createMaintenance(propertyId, dto);
    }

    @GetMapping("/societies/{societyId}/maintenance")
    public List<MaintenanceResponseDto> getBySociety(
            @PathVariable Long societyId
    ) {
        return maintenanceService.getBySociety(societyId);
    }

    @PutMapping("/maintenance/{id}/pay")
    public void pay(@PathVariable Long id) {
        maintenanceService.markAsPaid(id);
    }
    
    @GetMapping("/my/maintenance")
    public List<MaintenanceResponseDto> myMaintenance(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return maintenanceService.getMyMaintenance(
                user.getUserId(),
                user.getRole()
        );
    }
}
