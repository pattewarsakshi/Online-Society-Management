package com.society.management.service;

import java.util.List;

import com.society.management.dto.PropertyCreateRequestDto;
import com.society.management.dto.PropertyResponseDto;
import com.society.management.dto.PropertyUpdateRequestDto;

public interface PropertyService {

    PropertyResponseDto addProperty(
            Long societyId,
            PropertyCreateRequestDto request
    );

    void assignTenant(Long propertyId, Long tenantUserId);

    List<PropertyResponseDto> getPropertiesBySociety(Long societyId);
    
    PropertyResponseDto getPropertyById(Long societyId, Long propertyId);
    
    PropertyResponseDto updateProperty(
            Long societyId,
            Long propertyId,
            PropertyUpdateRequestDto request
    );

    void deleteProperty(Long societyId, Long propertyId);
    
    void unassignTenant(Long societyId, Long propertyId);
    

    void changeOwner(Long societyId, Long propertyId, Long newOwnerUserId);
    
    List<PropertyResponseDto> getPropertiesForOwner(String ownerEmail);


}
