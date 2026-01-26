package com.society.controller;

import com.society.dto.RegisterRequestDTO;
import com.society.dto.LoginRequestDTO;
import com.society.dto.LoginResponseDTO;
import com.society.entity.User;
import com.society.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    // SELF REGISTER (OWNER / RESIDENT)
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO request) {

        User user = userService.registerUser(request, false);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User registered successfully with ID: " + user.getUserId());
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO request,
            HttpSession session) {

        return ResponseEntity.ok(
                userService.loginUser(request, session)
        );
        
        
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 🔴 THIS IS MANDATORY
        }
        return ResponseEntity.ok("Logged out successfully");
    }

}
