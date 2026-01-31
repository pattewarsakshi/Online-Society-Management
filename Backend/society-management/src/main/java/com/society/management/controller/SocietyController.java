package com.society.management.controller;

import com.society.management.dto.SocietyRequestDto;
import com.society.management.dto.SocietyResponseDto;
import com.society.management.service.SocietyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Society APIs for SUPER_ADMIN
 * - Create society
 * - View societies created by logged-in SUPER_ADMIN
 */
@RestController
@RequestMapping("/api/societies")
@RequiredArgsConstructor
public class SocietyController {

    private final SocietyService societyService;

    /**
     * Create a new society
     * Accessible only by SUPER_ADMIN
     */
    @PostMapping
    public ResponseEntity<SocietyResponseDto> createSociety(
            @Valid @RequestBody SocietyRequestDto requestDto
    ) {
        SocietyResponseDto response = societyService.createSociety(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Get societies created by the logged-in SUPER_ADMIN
     */
    @GetMapping("/my")
    public List<SocietyResponseDto> getMySocieties() {
        return societyService.getMySocieties();
    }
}
