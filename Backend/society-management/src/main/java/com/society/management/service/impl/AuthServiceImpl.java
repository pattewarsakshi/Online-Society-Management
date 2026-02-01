package com.society.management.service.impl;

import com.society.management.dto.LoginRequestDto;
import com.society.management.dto.LoginResponseDto;
import com.society.management.dto.RegisterSuperAdminRequest;
import com.society.management.entity.User;
import com.society.management.enumtype.Role;
import com.society.management.repository.UserRepository;
import com.society.management.service.AuthService;
import com.society.management.util.JwtUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import com.society.management.dto.LoginRequestDto;
import com.society.management.dto.LoginResponseDto;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;



    @Override
    public void registerSuperAdmin(RegisterSuperAdminRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        if (userRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Phone already registered");
        }

        User superAdmin = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.SUPER_ADMIN)
                .build();

        userRepository.save(superAdmin);
    }
    
    @Override
    public LoginResponseDto login(LoginRequestDto request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(
                user.getUserId(),
                user.getEmail(),
                user.getRole().name(),
                user.getSociety() != null
                        ? user.getSociety().getSocietyId()
                        : null
        );

        return LoginResponseDto.builder()
                .token(token)
                .role(user.getRole().name())
                .societyId(
                    user.getSociety() != null
                        ? user.getSociety().getSocietyId()
                        : null
                )
                .build();
    }



}
