package com.society.management.controller;

import com.society.management.dto.AuthResponse;

import com.society.management.dto.LoginRequestDto;
import com.society.management.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequestDto loginRequest) {

        return ResponseEntity.ok(authService.login(loginRequest));
    }
}
