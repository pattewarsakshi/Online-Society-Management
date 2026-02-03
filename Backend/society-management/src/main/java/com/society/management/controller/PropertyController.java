package com.society.management.controller;

import com.society.management.dto.OwnerChangeRequestDto;
import com.society.management.dto.PropertyCardDto;
import com.society.management.dto.PropertyCreateRequestDto;
import com.society.management.dto.PropertyResponseDto;
import com.society.management.dto.PropertyUpdateRequestDto;
import com.society.management.dto.TenantAssignRequestDto;
import com.society.management.entity.Property;
import com.society.management.service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    
    //get single property by id
    @GetMapping("/{propertyId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PropertyResponseDto> getPropertyById(
            @PathVariable Long societyId,
            @PathVariable Long propertyId) {

        return ResponseEntity.ok(
                propertyService.getPropertyById(societyId, propertyId)
        );
    }
    
    @PutMapping("/{propertyId}")
    public ResponseEntity<PropertyResponseDto> updateProperty(
            @PathVariable Long societyId,
            @PathVariable Long propertyId,
            @Valid @RequestBody PropertyUpdateRequestDto dto
    ) {
        return ResponseEntity.ok(
                propertyService.updateProperty(societyId, propertyId, dto)
        );
    }
    
    @DeleteMapping("/{propertyId}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<String> deleteProperty(
            @PathVariable Long societyId,
            @PathVariable Long propertyId) {

        propertyService.deleteProperty(societyId, propertyId);
        return ResponseEntity.ok("Property deleted successfully");
    }
    
    @PutMapping("/{propertyId}/unassign-tenant")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> unassignTenant(
            @PathVariable Long societyId,
            @PathVariable Long propertyId) {

        propertyService.unassignTenant(societyId, propertyId);
        return ResponseEntity.ok("Tenant unassigned successfully");
    }
    
    @PutMapping("/{propertyId}/change-owner")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> changeOwner(
            @PathVariable("societyId") Long societyId,
            @PathVariable("propertyId") Long propertyId,
            @Valid @RequestBody OwnerChangeRequestDto request) {

        propertyService.changeOwner(
                societyId,
                propertyId,
                request.getNewOwnerUserId()
        );

        return ResponseEntity.ok("Owner changed successfully");
    }
    
    @GetMapping("/my")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<PropertyResponseDto>> getMyProperties(
            Authentication authentication) {

        String email = authentication.getName();
        return ResponseEntity.ok(
                propertyService.getPropertiesForOwner(email)
        );
    }
    
    @GetMapping("/cards")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<List<PropertyCardDto>> getPropertyCards(
            @PathVariable Long societyId) {

        return ResponseEntity.ok(
                propertyService.getPropertyCards(societyId)
        );
    }









}
