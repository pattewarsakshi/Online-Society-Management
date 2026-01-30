package com.society.management.service.impl;

import com.society.management.dto.UserRegisterRequestDto;
import com.society.management.dto.UserResponseDto;
import com.society.management.entity.Society;
import com.society.management.entity.User;
import com.society.management.exception.ResourceAlreadyExistsException;
import com.society.management.repository.SocietyRepository;
import com.society.management.repository.UserRepository;
import com.society.management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Business logic for user registration.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SocietyRepository societyRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto registerUser(UserRegisterRequestDto requestDto) {

        // Check duplicate email
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already registered");
        }

        // Fetch society
        Society society = societyRepository.findById(requestDto.getSocietyId())
                .orElseThrow(() -> new RuntimeException("Society not found"));

        // Encrypt password
        String encryptedPassword = passwordEncoder.encode(requestDto.getPassword());

        // Build User entity
        User user = User.builder()
                .fullName(requestDto.getFullName())
                .email(requestDto.getEmail())
                .phone(requestDto.getPhone())
                .role(requestDto.getRole())
                .password(encryptedPassword)
                .society(society)
                .build();

        User savedUser = userRepository.save(user);

        // Return response DTO
        return UserResponseDto.builder()
                .userId(savedUser.getUserId())
                .fullName(savedUser.getFullName())
                .email(savedUser.getEmail())
                .phone(savedUser.getPhone())
                .role(savedUser.getRole())
                .societyId(society.getSocietyId())
                .build();
    }
}
