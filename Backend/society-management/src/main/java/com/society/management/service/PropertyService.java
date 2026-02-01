package com.society.management.service;

import java.util.List;

import com.society.management.dto.PropertyCreateRequestDto;
import com.society.management.dto.PropertyResponseDto;

public interface PropertyService {

    PropertyResponseDto addProperty(
            Long societyId,
            PropertyCreateRequestDto request
    );

    void assignTenant(Long propertyId, Long tenantUserId);

    List<PropertyResponseDto> getPropertiesBySociety(Long societyId);
}
