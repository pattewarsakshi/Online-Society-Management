package com.society.management.controller;

import com.society.management.dto.MeResponseDto;
import com.society.management.dto.ResetPasswordRequestDto;
import com.society.management.dto.UserRegisterRequestDto;
import com.society.management.dto.UserResponseDto;
import com.society.management.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for user-related APIs.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Register a new user (ADMIN / SUPER_ADMIN only)
     */
    
    
    @PutMapping("/{userId}/reset-password")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<String> resetPassword(
            @PathVariable Long userId,
            @Valid @RequestBody ResetPasswordRequestDto request
    ) {
        userService.resetPassword(userId, request.getNewPassword());
        return ResponseEntity.ok("Password reset successfully");
    }
    
    @GetMapping("/me")
    public ResponseEntity<MeResponseDto> me() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }
}
