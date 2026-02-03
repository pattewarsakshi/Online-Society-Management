package com.society.management.controller;



import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.society.management.dto.AdminDashboardDto;
import com.society.management.service.AdminDashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/societies")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;

    /**
     * Admin Analytics Dashboard
     * Accessible by ADMIN and SUPER_ADMIN only
     */
    @GetMapping("/{societyId}/admin/dashboard")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<AdminDashboardDto> getAdminDashboard(
            @PathVariable Long societyId) {

        return ResponseEntity.ok(
                adminDashboardService.getDashboard(societyId)
        );
    }
}
