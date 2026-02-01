package com.society.management.controller;

import com.society.management.dto.TenantAssignRequestDto;
import com.society.management.service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    @PutMapping("/{propertyId}/assign-tenant")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> assignTenant(
            @PathVariable Long propertyId,
            @Valid @RequestBody TenantAssignRequestDto request
    ) {
        propertyService.assignTenant(propertyId, request.getTenantUserId());
        return ResponseEntity.ok("Tenant assigned successfully");
    }
}
