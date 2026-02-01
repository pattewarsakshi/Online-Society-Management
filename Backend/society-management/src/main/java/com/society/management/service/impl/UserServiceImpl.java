package com.society.management.service.impl;

import com.society.management.dto.UserRegisterRequestDto;
import com.society.management.dto.UserResponseDto;
import com.society.management.entity.Society;
import com.society.management.entity.User;
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

    public UserResponseDto createUser(UserRegisterRequestDto request) {

        // Check duplicate email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Fetch society if provided
        Society society = null;
        if (request.getSocietyId() != null) {
            society = societyRepository.findById(request.getSocietyId())
                    .orElseThrow(() -> new RuntimeException("Society not found"));
        }

        // Build User entity
        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .role(request.getRole())
                .password(passwordEncoder.encode(request.getPassword())) // ALWAYS encode
                .society(society)
                .build();

        User savedUser = userRepository.save(user);

        // Convert to response DTO
        return UserResponseDto.builder()
                .userId(savedUser.getUserId())
                .fullName(savedUser.getFullName())
                .email(savedUser.getEmail())
                .phone(savedUser.getPhone())
                .role(savedUser.getRole())
                .societyId(
                        savedUser.getSociety() != null
                                ? savedUser.getSociety().getSocietyId()
                                : null
                )
                .build();
    }



    @Override
    @Transactional
    public UserResponseDto registerUser(UserRegisterRequestDto request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Society society = null;
        if (request.getSocietyId() != null) {
            society = societyRepository.findById(request.getSocietyId())
                    .orElseThrow(() -> new RuntimeException("Society not found"));
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .role(request.getRole())
                .password(passwordEncoder.encode(request.getPassword()))
                .society(society)
                .build();

        User savedUser = userRepository.save(user);

        return UserResponseDto.builder()
                .userId(savedUser.getUserId())
                .fullName(savedUser.getFullName())
                .email(savedUser.getEmail())
                .phone(savedUser.getPhone())
                .role(savedUser.getRole())
                .societyId(
                        savedUser.getSociety() != null
                                ? savedUser.getSociety().getSocietyId()
                                : null
                )
                .build();
    }

    @Override
    @Transactional
    public void resetPassword(Long userId, String newPassword) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
	}

