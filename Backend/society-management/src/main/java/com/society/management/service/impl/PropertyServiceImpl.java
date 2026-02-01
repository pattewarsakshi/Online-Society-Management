package com.society.management.service.impl;


import com.society.management.dto.PropertyCreateRequestDto;
import com.society.management.dto.PropertyResponseDto;
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

        // 1️⃣ Property must exist
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        // 2️⃣ Tenant must exist
        User tenant = userRepository.findById(tenantUserId)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));

        // 3️⃣ User must be TENANT
        if (tenant.getRole() != Role.TENANT) {
            throw new RuntimeException("User is not a TENANT");
        }

        // 4️⃣ Assign tenant + update status
        property.setTenant(tenant);
        property.setStatus(PropertyStatus.OCCUPIED);

        // 5️⃣ Persist
        propertyRepository.save(property);
    }

    @Override
    public List<PropertyResponseDto> getPropertiesBySociety(Long societyId) {
        return propertyRepository.findBySociety_SocietyId(societyId)
                .stream()
                .map(property -> PropertyResponseDto.builder()
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
                        .build()
                )
                .collect(Collectors.toList());
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




}
