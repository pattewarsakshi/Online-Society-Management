package com.society.management.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.society.management.dto.AdminUserTableDto;
import com.society.management.enumtype.Role;
import com.society.management.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/societies")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    @GetMapping("/{societyId}/users")
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public ResponseEntity<List<AdminUserTableDto>> getAdminUsers(
            @PathVariable Long societyId,
            @RequestParam(required = false) Role role) {

        return ResponseEntity.ok(
                userService.getAdminUsers(societyId, role)
        );
    }
}
