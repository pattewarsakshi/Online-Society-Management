package com.society.management.controller;

import com.society.management.dto.SocietyRequestDto;
import com.society.management.dto.SocietyResponseDto;
import com.society.management.service.SocietyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for society-related APIs.
 */
@RestController
@RequestMapping("/api/societies")
@RequiredArgsConstructor
public class SocietyController {

    private final SocietyService societyService;

    /**
     * Create a new society.
     * (Later restricted to SUPER_ADMIN)
     */
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<SocietyResponseDto> createSociety(
            @Valid @RequestBody SocietyRequestDto requestDto) {

        SocietyResponseDto response = societyService.createSociety(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

