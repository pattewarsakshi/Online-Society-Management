package com.society.management.controller;

import com.society.management.dto.LoginRequestDto;
import com.society.management.dto.LoginResponseDto;
import com.society.management.dto.RegisterSuperAdminRequest;
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

    // ✅ REGISTER SUPER ADMIN
    @PostMapping("/register-super-admin")
    public ResponseEntity<String> registerSuperAdmin(
            @Valid @RequestBody RegisterSuperAdminRequest request) {

        authService.registerSuperAdmin(request);
        return ResponseEntity.ok("SUPER_ADMIN registered successfully");
    }

    // ✅ LOGIN (JWT)
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto request) {

        return ResponseEntity.ok(authService.login(request));
    }
}
