package com.society.management.service.impl;


import com.society.management.dto.PropertyCreateRequestDto;
import com.society.management.dto.PropertyResponseDto;
import com.society.management.dto.PropertyUpdateRequestDto;
import com.society.management.entity.Property;
import com.society.management.entity.Society;
import com.society.management.entity.User;
import com.society.management.enumtype.PropertyStatus;
import com.society.management.enumtype.Role;
import com.society.management.repository.PropertyRepository;
import com.society.management.repository.SocietyRepository;
import com.society.management.repository.UserRepository;
import com.society.management.service.PropertyService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final SocietyRepository societyRepository;
    
    @Override
    @Transactional
    public void assignTenant(Long propertyId, Long tenantUserId) {

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        // ❌ Block if property is DELETED
        if (property.getStatus() == PropertyStatus.DELETED) {
            throw new RuntimeException("Cannot assign tenant to deleted property");
        }

        User tenant = userRepository.findById(tenantUserId)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));

        if (tenant.getRole() != Role.TENANT) {
            throw new RuntimeException("User is not a TENANT");
        }

        property.setTenant(tenant);
        property.setStatus(PropertyStatus.OCCUPIED);

        propertyRepository.save(property);
    }

    @Override
    public PropertyResponseDto addProperty(Long societyId, PropertyCreateRequestDto request) {

        // 1️⃣ Fetch Society
        Society society = societyRepository.findById(societyId)
                .orElseThrow(() -> new RuntimeException("Society not found"));

        // 2️⃣ Fetch Owner
        User owner = userRepository.findById(request.getOwnerUserId())
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        if (owner.getRole() != Role.OWNER) {
            throw new RuntimeException("User is not an OWNER");
        }
        // 3️⃣ Optional: prevent duplicate flats
        boolean exists = propertyRepository
                .existsBySociety_SocietyIdAndFlatNumberAndBlock(
                        societyId,
                        request.getFlatNumber(),
                        request.getBlock()
                );

        if (exists) {
            throw new RuntimeException("Property already exists in this society");
        }

        // 4️⃣ Create Property
        Property property = Property.builder()
                .society(society)
                .flatNumber(request.getFlatNumber())
                .block(request.getBlock())
                .floorNumber(request.getFloorNumber())
                .areaSqft(request.getAreaSqft())
                .owner(owner)
                .status(PropertyStatus.VACANT)
                .build();

        Property saved = propertyRepository.save(property);

        // 5️⃣ Map → Response
        return PropertyResponseDto.builder()
                .propertyId(saved.getPropertyId())
                .flatNumber(saved.getFlatNumber())
                .block(saved.getBlock())
                .floorNumber(saved.getFloorNumber())
                .areaSqft(saved.getAreaSqft())
                .status(saved.getStatus().name())
                .ownerId(owner.getUserId())
                .ownerName(owner.getFullName())
                .build();
    }
    
    @Override
    public PropertyResponseDto getPropertyById(Long societyId, Long propertyId) {

        Property property = propertyRepository
                .findByPropertyIdAndSociety_SocietyId(propertyId, societyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        return mapToDto(property);
    }
    
    @Override
    @Transactional
    public PropertyResponseDto updateProperty(
            Long societyId,
            Long propertyId,
            PropertyUpdateRequestDto request) {

        // 1️⃣ Property must exist AND belong to society
        Property property = propertyRepository
                .findByPropertyIdAndSociety_SocietyId(propertyId, societyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        // 2️⃣ Cannot update deleted property
        if (property.getStatus() == PropertyStatus.DELETED) {
            throw new RuntimeException("Cannot update deleted property");
        }

        // 3️⃣ Update ONLY allowed fields
        property.setFloorNumber(request.getFloorNumber());
        property.setAreaSqft(request.getAreaSqft());

        // 4️⃣ Persist & return
        Property updated = propertyRepository.save(property);
        return mapToDto(updated);
    }

    
    @Override
    @Transactional
    public void deleteProperty(Long societyId, Long propertyId) {

        Property property = propertyRepository
                .findByPropertyIdAndSociety_SocietyId(propertyId, societyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        // ❌ Cannot delete if tenant exists
        if (property.getTenant() != null) {
            throw new RuntimeException("Cannot delete property with assigned tenant");
        }

        // ❌ Already deleted
        if (property.getStatus() == PropertyStatus.DELETED) {
            throw new RuntimeException("Property already deleted");
        }

        // ✅ Soft delete
        property.setStatus(PropertyStatus.DELETED);
        propertyRepository.save(property);
    }
    
    private PropertyResponseDto mapToDto(Property property) {

        return PropertyResponseDto.builder()
                .propertyId(property.getPropertyId())
                .flatNumber(property.getFlatNumber())
                .block(property.getBlock())
                .floorNumber(property.getFloorNumber())
                .areaSqft(property.getAreaSqft())
                .status(property.getStatus().name())
                .ownerId(property.getOwner().getUserId())
                .ownerName(property.getOwner().getFullName())
                .tenantId(
                    property.getTenant() != null
                        ? property.getTenant().getUserId()
                        : null
                )
                .tenantName(
                    property.getTenant() != null
                        ? property.getTenant().getFullName()
                        : null
                )
                .build();
    }
    
    @Override
    public List<PropertyResponseDto> getPropertiesBySociety(Long societyId) {

        return propertyRepository
                .findBySociety_SocietyIdAndStatusNot(
                        societyId,
                        PropertyStatus.DELETED
                )
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    
    
    @Override
    @Transactional
    public void unassignTenant(Long societyId, Long propertyId) {

        Property property = propertyRepository
                .findByPropertyIdAndSociety_SocietyId(propertyId, societyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        if (property.getStatus() == PropertyStatus.DELETED) {
            throw new RuntimeException("Cannot unassign tenant from deleted property");
        }

        if (property.getTenant() == null) {
            throw new RuntimeException("No tenant assigned to this property");
        }

        property.setTenant(null);
        property.setStatus(PropertyStatus.VACANT);

        propertyRepository.save(property);
    }

    @Override
    @Transactional
    public void changeOwner(Long societyId, Long propertyId, Long newOwnerUserId) {

        Property property = propertyRepository
                .findByPropertyIdAndSociety_SocietyId(propertyId, societyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        if (property.getStatus() == PropertyStatus.DELETED) {
            throw new RuntimeException("Cannot change owner of deleted property");
        }

        User newOwner = userRepository.findById(newOwnerUserId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        if (newOwner.getRole() != Role.OWNER) {
            throw new RuntimeException("User is not an OWNER");
        }

        property.setOwner(newOwner);
        propertyRepository.save(property);
    }
    
    
    @Override
    public List<PropertyResponseDto> getPropertiesForOwner(String ownerEmail) {

        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        return propertyRepository
                .findByOwner_UserIdAndStatusNot(
                        owner.getUserId(),
                        PropertyStatus.DELETED
                )
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }





}
