package com.society.management.service.impl;

import com.society.management.dto.MeResponseDto;
import com.society.management.dto.UserRegisterRequestDto;
import com.society.management.dto.UserResponseDto;
import com.society.management.entity.Society;
import com.society.management.entity.User;
import com.society.management.enumtype.Role;
import com.society.management.repository.SocietyRepository;
import com.society.management.repository.UserRepository;
import com.society.management.service.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SocietyRepository societyRepository;
    private final PasswordEncoder passwordEncoder;
    
    //================================================================

    @Override
    @Transactional
    public User createUserInternal(
            UserRegisterRequestDto request,
            Role role,
            Society society
    ) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Phone already exists");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)              // ✅ role set HERE
                .society(society)
                .build();

        return userRepository.save(user);
    }

    //============================================================
    //for dashbboard purpose
    @Override
    public MeResponseDto getCurrentUser() {

        String email = org.springframework.security.core.context
                .SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return MeResponseDto.builder()
                .userId(user.getUserId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole().name())
                .societyId(
                        user.getSociety() != null
                                ? user.getSociety().getSocietyId()
                                : null
                )
                .societyName(
                        user.getSociety() != null
                                ? user.getSociety().getSocietyName()
                                : null
                )
                .build();
    }
    //================================================================
    @Override
    @Transactional
    public void resetPassword(Long userId, String newPassword) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
    }
    
    //=============================================================


	}

