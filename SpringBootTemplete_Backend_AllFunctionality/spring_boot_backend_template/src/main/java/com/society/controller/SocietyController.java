package com.society.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.society.entity.Society;
import com.society.service.SocietyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/societies")
@RequiredArgsConstructor
public class SocietyController {

    private final SocietyService societyService;

    // 1️⃣ Create Society
    @PostMapping
    public ResponseEntity<Society> createSociety(@RequestBody Society society) {
        Society savedSociety = societyService.createSociety(society);
        return new ResponseEntity<>(savedSociety, HttpStatus.CREATED);
    }

    // 2️⃣ Get Society by ID (used internally during registration)
    @GetMapping("/{societyId}")
    public ResponseEntity<Society> getSocietyById(@PathVariable Integer societyId) {
        Society society = societyService.getSocietyById(societyId);
        return ResponseEntity.ok(society);
    }
}
