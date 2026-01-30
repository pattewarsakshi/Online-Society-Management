package com.society.management.service.impl;

import com.society.management.dto.LoginRequestDto;
import com.society.management.dto.LoginResponseDto;
import com.society.management.entity.User;
import com.society.management.repository.UserRepository;
import com.society.management.service.AuthService;
import com.society.management.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Authentication service implementation.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public LoginResponseDto login(LoginRequestDto requestDto) {

        // Fetch user by email
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // Validate password
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // Prepare JWT claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());
        claims.put("societyId", user.getSociety().getSocietyId());

        // Generate token
        String token = jwtUtil.generateToken(user.getEmail(), claims);

        return LoginResponseDto.builder()
                .token(token)
                .role(user.getRole().name())
                .societyId(user.getSociety().getSocietyId())
                .build();
    }
}

