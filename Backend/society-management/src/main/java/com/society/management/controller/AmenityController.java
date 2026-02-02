package com.society.management.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.society.management.dto.AmenityResponseDto;
import com.society.management.dto.CreateAmenityRequestDto;
import com.society.management.service.AmenityService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/societies/{societyId}/amenities")
@RequiredArgsConstructor
public class AmenityController {

    private final AmenityService amenityService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<AmenityResponseDto> createAmenity(
            @PathVariable Long societyId,
            @RequestBody @Valid CreateAmenityRequestDto dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(amenityService.createAmenity(societyId, dto));
    }
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN','OWNER','TENANT')")
    public List<AmenityResponseDto> getAmenities(
            @PathVariable Long societyId
    ) {
        return amenityService.getAmenitiesBySociety(societyId);
    }

}