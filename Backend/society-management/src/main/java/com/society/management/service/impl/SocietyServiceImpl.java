package com.society.management.service.impl;

import com.society.management.dto.SocietyRequestDto;
import com.society.management.dto.SocietyResponseDto;
import com.society.management.entity.Society;
import com.society.management.entity.User;
import com.society.management.exception.ResourceAlreadyExistsException;
import com.society.management.repository.SocietyRepository;
import com.society.management.repository.UserRepository;
import com.society.management.security.CustomUserDetails;
import com.society.management.service.SocietyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.society.management.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SocietyServiceImpl implements SocietyService {

    private final SocietyRepository societyRepository;
    private final UserRepository userRepository;

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


}
