package com.society.management.service.impl;


import com.society.management.entity.Property;
import com.society.management.entity.User;
import com.society.management.enumtype.PropertyStatus;
import com.society.management.enumtype.Role;
import com.society.management.repository.PropertyRepository;
import com.society.management.repository.UserRepository;
import com.society.management.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

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
}
