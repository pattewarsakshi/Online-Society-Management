package com.society.management.service.impl;

import com.society.management.dto.SocietyRequestDto;
import com.society.management.dto.SocietyResponseDto;
import com.society.management.entity.Society;
import com.society.management.exception.ResourceAlreadyExistsException;
import com.society.management.repository.SocietyRepository;
import com.society.management.service.SocietyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Implementation of SocietyService.
 * Contains business logic.
 */
@Service
@RequiredArgsConstructor
public class SocietyServiceImpl implements SocietyService {

	private final SocietyRepository societyRepository;

    @Override
    public SocietyResponseDto createSociety(SocietyRequestDto requestDto) {

        // Check if society with same name already exists
        societyRepository.findByName(requestDto.getName())
                .ifPresent(society -> {
                    throw new ResourceAlreadyExistsException(
                            "Society with this name already exists");
                });
        
     // Convert DTO to Entity
        Society society = Society.builder()
                .name(requestDto.getName())
                .address(requestDto.getAddress())
                .city(requestDto.getCity())
                .state(requestDto.getState())
                .pincode(requestDto.getPincode())
                .build();
        
     // Save to database
        Society savedSociety = societyRepository.save(society);

        // Convert Entity to Response DTO
        return SocietyResponseDto.builder()
                .societyId(savedSociety.getSocietyId())
                .name(savedSociety.getName())
                .address(savedSociety.getAddress())
                .city(savedSociety.getCity())
                .state(savedSociety.getState())
                .pincode(savedSociety.getPincode())
                .build();
    }
}
