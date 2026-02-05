package com.society.management.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.society.management.dto.CreateVisitorRequestDto;
import com.society.management.dto.VisitorResponseDto;
import com.society.management.service.VisitorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/guard/societies/{societyId}/visitors")
@RequiredArgsConstructor
public class GuardVisitorController {

    private final VisitorService visitorService;

    @PostMapping
    @PreAuthorize("hasRole('GUARD')")
    public ResponseEntity<VisitorResponseDto> registerVisitor(
            @PathVariable Long societyId,
            @RequestBody @Valid CreateVisitorRequestDto dto,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                visitorService.registerVisitor(
                        societyId,
                        dto,
                        authentication.getName()
                )
        );
    }

    @GetMapping("/today")
    @PreAuthorize("hasRole('GUARD')")
    public ResponseEntity<List<VisitorResponseDto>> getTodayVisitors(
            @PathVariable Long societyId
    ) {
        return ResponseEntity.ok(
                visitorService.getTodayVisitors(societyId)
        );
    }

    @PutMapping("/{visitorId}/exit")
    @PreAuthorize("hasRole('GUARD')")
    public ResponseEntity<String> markExit(
            @PathVariable Long visitorId,
            Authentication authentication
    ) {
        visitorService.markExit(visitorId, authentication.getName());
        return ResponseEntity.ok("Visitor exit marked");
    }
}
