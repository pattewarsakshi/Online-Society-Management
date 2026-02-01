package com.society.management.controller;

import com.society.management.dto.PropertyCreateRequestDto;
import com.society.management.dto.PropertyResponseDto;
import com.society.management.dto.TenantAssignRequestDto;
import com.society.management.service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/societies/{societyId}/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    // ADD PROPERTY
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<PropertyResponseDto> addProperty(
            @PathVariable Long societyId,
            @RequestBody @Valid PropertyCreateRequestDto request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(propertyService.addProperty(societyId, request));
    }

    // GET ALL PROPERTIES OF SOCIETY
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<PropertyResponseDto>> getPropertiesBySociety(
            @PathVariable Long societyId) {

        return ResponseEntity.ok(
                propertyService.getPropertiesBySociety(societyId)
        );
    }

    // ASSIGN TENANT
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{propertyId}/assign-tenant")
    public ResponseEntity<String> assignTenant(
            @PathVariable Long propertyId,
            @RequestBody @Valid TenantAssignRequestDto request) {

        propertyService.assignTenant(propertyId, request.getTenantUserId());
        return ResponseEntity.ok("Tenant assigned successfully");
    }
}
