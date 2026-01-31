package com.society.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.society.dto.VisitorEntryDTO;
import com.society.service.VisitorService;

@RestController
@RequestMapping("/api/visitors")
public class VisitorController {

    private final VisitorService visitorService;

    public VisitorController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    // ➕ ADD VISITOR
    @PostMapping("/society/{societyId}/user/{userId}")
    public ResponseEntity<?> addVisitor(
            @PathVariable Integer societyId,
            @PathVariable Integer userId,
            @RequestBody VisitorEntryDTO dto) {

        return ResponseEntity.ok(
                visitorService.addVisitor(societyId, userId, dto)
        );
    }

    // 🚪 EXIT VISITOR
    @PutMapping("/society/{societyId}/exit/{visitorId}")
    public ResponseEntity<?> exitVisitor(
            @PathVariable Integer societyId,
            @PathVariable Integer visitorId) {

        return ResponseEntity.ok(
                visitorService.exitVisitor(societyId, visitorId)
        );
    }

    // 👥 INSIDE VISITORS
    @GetMapping("/society/{societyId}/inside")
    public ResponseEntity<?> insideVisitors(@PathVariable Integer societyId) {
        return ResponseEntity.ok(
                visitorService.getInsideVisitors(societyId)
        );
    }

    // 📅 TODAY VISITORS
    @GetMapping("/society/{societyId}/today")
    public ResponseEntity<?> todayVisitors(@PathVariable Integer societyId) {
        return ResponseEntity.ok(
                visitorService.getTodayVisitors(societyId)
        );
    }

    // 📜 ALL VISITORS
    @GetMapping("/society/{societyId}")
    public ResponseEntity<?> allVisitors(@PathVariable Integer societyId) {
        return ResponseEntity.ok(
                visitorService.getAllVisitors(societyId)
        );
    }
}
