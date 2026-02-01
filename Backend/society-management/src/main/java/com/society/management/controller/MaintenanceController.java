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

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MaintenanceController {

    private final MaintenanceService maintenanceService;
    
    
     //=====================================================
    @PostMapping("/societies/{societyId}/maintenance")
    @PreAuthorize("hasRole('ADMIN')")
    public MaintenanceResponseDto createMaintenance(
            @PathVariable Long societyId,
            @RequestBody CreateMaintenanceRequestDto dto
    ) {
        return maintenanceService.createMaintenanceForSociety(societyId, dto);
    }

    //===================================================
    @GetMapping("/societies/{societyId}/maintenance")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public List<MaintenanceResponseDto> getBySociety(
            @PathVariable Long societyId
    ) {
        return maintenanceService.getBySociety(societyId);
    }

    //========================================================

    @PutMapping("/societies/{societyId}/maintenance/{id}/pay")
    @PreAuthorize("hasRole('OWNER')")
    public void payMaintenance(
            @PathVariable Long societyId,
            @PathVariable Long id
    ) {
        maintenanceService.markAsPaid(societyId, id);
    }
    //=========================================================
    @GetMapping("/societies/{societyId}/maintenance/my")
    @PreAuthorize("hasRole('OWNER')")
    public List<MaintenanceResponseDto> getMyMaintenance(
            @PathVariable Long societyId,
            Authentication authentication
    ) {
        return maintenanceService.getMaintenanceForOwner(
                societyId,
                authentication.getName()
        );
    }
}
