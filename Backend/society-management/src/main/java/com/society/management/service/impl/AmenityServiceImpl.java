package com.society.management.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.society.management.dto.AmenityResponseDto;
import com.society.management.dto.CreateAmenityRequestDto;
import com.society.management.entity.Amenity;
import com.society.management.entity.Society;
import com.society.management.enumtype.AmenityStatus;
import com.society.management.repository.AmenityRepository;
import com.society.management.repository.SocietyRepository;
import com.society.management.service.AmenityService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AmenityServiceImpl implements AmenityService {

    private final AmenityRepository amenityRepository;
    private final SocietyRepository societyRepository;

    public AmenityResponseDto createAmenity(
            Long societyId,
            CreateAmenityRequestDto dto
    ) {
        // 1️⃣ Validate society
        Society society = societyRepository.findById(societyId)
                .orElseThrow(() -> new RuntimeException("Society not found"));

        // 2️⃣ Prevent duplicate amenity
        if (amenityRepository.existsBySociety_SocietyIdAndNameIgnoreCase(
                societyId,
                dto.getName()
        )) {
            throw new RuntimeException("Amenity already exists in society");
        }

        // 3️⃣ Create amenity
        Amenity amenity = Amenity.builder()
                .society(society)
                .name(dto.getName())
                .description(dto.getDescription())
                .status(AmenityStatus.ACTIVE)
                .build();

        Amenity saved = amenityRepository.save(amenity);

        // 4️⃣ Map response
        return AmenityResponseDto.builder()
                .amenityId(saved.getAmenityId())
                .name(saved.getName())
                .description(saved.getDescription())
                .status(saved.getStatus().name())
                .build();
    }
    
    
    @Override
    public List<AmenityResponseDto> getAmenitiesBySociety(Long societyId) {

        Society society = societyRepository.findById(societyId)
                .orElseThrow(() -> new RuntimeException("Society not found"));
        return amenityRepository.findBySociety(society)
                .stream()
                .map(a -> {
                    AmenityResponseDto dto = new AmenityResponseDto();
                    dto.setAmenityId(a.getAmenityId());
                    dto.setName(a.getName());
                    dto.setStartTime(a.getStartTime());
                    dto.setEndTime(a.getEndTime());
                    dto.setStatus(a.getStatus().name());
                    return dto;
                })
                .toList();

        
    }

}
