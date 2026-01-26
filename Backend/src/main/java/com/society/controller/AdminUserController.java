package com.society.controller;

import com.society.dto.RegisterRequestDTO;
import com.society.entity.User;
import com.society.entityenum.Role;
import com.society.service.UserService;

import jakarta.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<?> createUserByAdmin(
            @RequestBody RegisterRequestDTO request,
            HttpSession session) {

        User loggedIn = (User) session.getAttribute("LOGGED_IN_USER");

        if (loggedIn == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Please login as admin");
        }

        if (loggedIn.getRole() != Role.ADMIN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Access denied");
        }

        // Admin can ONLY create ADMIN or GUARD
        if (request.getRole() != Role.ADMIN &&
            request.getRole() != Role.GUARD) {
            return ResponseEntity.badRequest()
                    .body("Admin can create only ADMIN or GUARD");
        }

        User user = userService.registerUser(request, true);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User created with ID: " + user.getUserId());
    }
}
