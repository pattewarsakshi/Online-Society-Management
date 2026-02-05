package com.society.management.controller;

import com.society.management.dto.GuardDashboardResponseDto;
import com.society.management.dto.VisitorEntryRequestDto;
import com.society.management.dto.VisitorEntryResponseDto;
import com.society.management.service.VisitorEntryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/societies/{societyId}/visitors")
@RequiredArgsConstructor
public class VisitorEntryController {

    private final VisitorEntryService visitorEntryService;

    @PostMapping
    @PreAuthorize("hasRole('GUARD')")
    public ResponseEntity<VisitorEntryResponseDto> createEntry(
            @PathVariable Long societyId,
            @Valid @RequestBody VisitorEntryRequestDto request,
            Authentication authentication
    ) {
        return ResponseEntity.ok(
                visitorEntryService.createEntry(
                        societyId,
                        request,
                        authentication.getName()
                )
        );
    }

    @PutMapping("/{visitorEntryId}/exit")
    @PreAuthorize("hasRole('GUARD')")
    public ResponseEntity<String> markExit(
            @PathVariable Long visitorEntryId
    ) {
        visitorEntryService.markExit(visitorEntryId);
        return ResponseEntity.ok("Visitor exit marked");
    }
    
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('GUARD')")
    public ResponseEntity<GuardDashboardResponseDto> getDashboard(
            @PathVariable Long societyId
    ) {
        return ResponseEntity.ok(
                visitorEntryService.getGuardDashboard(societyId)
        );
    }

}
