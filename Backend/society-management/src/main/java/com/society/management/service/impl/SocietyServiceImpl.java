package com.society.management.service.impl;

import com.society.management.dto.AdminCreateRequestDto;
import com.society.management.exception.OwnerAlreadyExistsException;

import com.society.management.dto.AdminResponseDto;
import com.society.management.dto.OwnerRegisterRequestDto;
import com.society.management.dto.SocietyRequestDto;
import com.society.management.dto.SocietyResponseDto;
import com.society.management.entity.Property;
import com.society.management.entity.Society;
import com.society.management.entity.User;
import com.society.management.enumtype.Role;
import com.society.management.exception.ResourceAlreadyExistsException;
import com.society.management.repository.PropertyRepository;
import com.society.management.repository.SocietyRepository;
import com.society.management.repository.UserRepository;
import com.society.management.security.CustomUserDetails;
import com.society.management.service.SocietyService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.society.management.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;




import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SocietyServiceImpl implements SocietyService {

    private final SocietyRepository societyRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PropertyRepository propertyRepository;

    @Override
    public SocietyResponseDto createSociety(SocietyRequestDto requestDto) {

        // 1️⃣ Prevent duplicate society
        societyRepository.findBySocietyName(requestDto.getSocietyName())
                .ifPresent(s -> {
                    throw new ResourceAlreadyExistsException("Society already exists");
                });

        // 2️⃣ Get logged-in user (SECURITY)
        CustomUserDetails userDetails =
                (CustomUserDetails) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        // 3️⃣ Fetch User entity from DB
        User creator = userRepository.findById(userDetails.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 4️⃣ Create society
        Society society = Society.builder()
                .societyName(requestDto.getSocietyName())
                .address(requestDto.getAddress())
                .city(requestDto.getCity())
                .state(requestDto.getState())
                .pincode(requestDto.getPincode())
                .createdBy(creator)
                .build();

        Society saved = societyRepository.save(society);

        // 5️⃣ Response
        return SocietyResponseDto.builder()
                .societyId(saved.getSocietyId())
                .societyName(saved.getSocietyName())
                .address(saved.getAddress())
                .city(saved.getCity())
                .state(saved.getState())
                .pincode(saved.getPincode())
                .createdAt(saved.getCreatedAt())
                .build();
    }


    @Override
    public List<SocietyResponseDto> getMySocieties() {

        CustomUserDetails currentUser =
            (CustomUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        User user = userRepository.findById(currentUser.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Society> societies =
                societyRepository.findByCreatedBy(user);

        return societies.stream()
                .map(s -> SocietyResponseDto.builder()
                        .societyId(s.getSocietyId())
                        .societyName(s.getSocietyName())
                        .address(s.getAddress())
                        .city(s.getCity())
                        .state(s.getState())
                        .pincode(s.getPincode())
                        .createdAt(s.getCreatedAt())
                        .build())
                .toList();
    }
    
    @Override
    public AdminResponseDto createAdmin(Long societyId, AdminCreateRequestDto request) {

        // 1️⃣ Check society exists
        Society society = societyRepository.findById(societyId)
                .orElseThrow(() -> new RuntimeException("Society not found"));

        // 2️⃣ One ADMIN per society check
        boolean adminExists =
                userRepository.existsBySociety_SocietyIdAndRole(
                        societyId, Role.ADMIN
                );

        if (adminExists) {
            throw new RuntimeException("Admin already exists for this society");
        }

        // 3️⃣ Email / phone uniqueness
        if (userRepository.existsByEmailOrPhone(
                request.getEmail(), request.getPhone())) {
        	throw new ResponseStatusException(
        		    HttpStatus.CONFLICT,
        		    "Only one admin is allowed per society"
        		);
        }

        // 4️⃣ Create ADMIN
        User admin = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .society(society)
                .build();

        User savedAdmin = userRepository.save(admin);

        // 5️⃣ Response
        return AdminResponseDto.builder()
                .userId(savedAdmin.getUserId())
                .fullName(savedAdmin.getFullName())
                .email(savedAdmin.getEmail())
                .role(savedAdmin.getRole().name())
                .societyId(societyId)
                .build();
    }
    
    
    @Override
    public void createOwner(Long societyId, OwnerRegisterRequestDto request) {

        Society society = societyRepository.findById(societyId)
                .orElseThrow(() -> new RuntimeException("Society not found"));

        if (userRepository.existsByEmailOrPhone(
                request.getEmail(), request.getPhone())) {
        	throw new OwnerAlreadyExistsException("Owner already exists");
  
        }

        User owner = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.OWNER)
                .society(society)
                .build();

        userRepository.save(owner);
    }

   
}
