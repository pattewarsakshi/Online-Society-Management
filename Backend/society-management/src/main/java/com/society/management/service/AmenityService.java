package com.society.management.service;

import java.util.List;

import com.society.management.dto.AmenityResponseDto;
import com.society.management.dto.CreateAmenityRequestDto;

public interface AmenityService {

    AmenityResponseDto createAmenity(
            Long societyId,
            CreateAmenityRequestDto dto
    );
    
    
        List<AmenityResponseDto> getAmenitiesBySociety(Long societyId);
   

}