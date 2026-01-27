package com.society.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.society.dto.MaintenanceRequestDTO;
import com.society.dto.MaintenanceResponseDTO;
import com.society.service.MaintenanceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/maintenance")
@RequiredArgsConstructor
public class AdminMaintenanceController {

    private final MaintenanceService maintenanceService;

    @PostMapping
    public ResponseEntity<MaintenanceResponseDTO> create(
            @RequestBody MaintenanceRequestDTO dto) {
        return ResponseEntity.ok(maintenanceService.createMaintenance(dto));
    }

    @GetMapping
    public ResponseEntity<List<MaintenanceResponseDTO>> getAll() {
        return ResponseEntity.ok(maintenanceService.getAllMaintenance());
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<MaintenanceResponseDTO> approve(@PathVariable Integer id) {
        return ResponseEntity.ok(maintenanceService.approvePayment(id));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<MaintenanceResponseDTO> reject(@PathVariable Integer id) {
        return ResponseEntity.ok(maintenanceService.rejectPayment(id));
    }
}

