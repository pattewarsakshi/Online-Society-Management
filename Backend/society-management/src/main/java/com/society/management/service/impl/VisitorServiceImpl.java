package com.society.management.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.society.management.dto.CreateVisitorRequestDto;
import com.society.management.dto.VisitorResponseDto;
import com.society.management.entity.Property;
import com.society.management.entity.Society;
import com.society.management.entity.User;
import com.society.management.entity.Visitor;
import com.society.management.repository.PropertyRepository;
import com.society.management.repository.SocietyRepository;
import com.society.management.repository.UserRepository;
import com.society.management.repository.VisitorRepository;
import com.society.management.service.VisitorService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository visitorRepository;
    private final UserRepository userRepository;
    private final SocietyRepository societyRepository;
    private final PropertyRepository propertyRepository;

    @Override
    public VisitorResponseDto registerVisitor(
            Long societyId,
            CreateVisitorRequestDto dto,
            String guardEmail) {

        User guard = userRepository.findByEmail(guardEmail)
                .orElseThrow(() -> new RuntimeException("Guard not found"));

        Society society = societyRepository.findById(societyId)
                .orElseThrow(() -> new RuntimeException("Society not found"));

        Property property = null;
        if (dto.getPropertyId() != null) {
            property = propertyRepository.findById(dto.getPropertyId())
                    .orElseThrow(() -> new RuntimeException("Property not found"));
        }

        Visitor visitor = Visitor.builder()
                .name(dto.getName())
                .phone(dto.getPhone())
                .purpose(dto.getPurpose())
                .property(property)
                .society(society)
                .createdBy(guard)
                .entryTime(LocalDateTime.now())
                .build();

        visitorRepository.save(visitor);

        return mapToDto(visitor);
    }

    @Override
    public List<VisitorResponseDto> getTodayVisitors(Long societyId) {

        return visitorRepository.findTodayVisitors(societyId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public void markExit(Long visitorId, String guardEmail) {

        Visitor visitor = visitorRepository.findById(visitorId)
                .orElseThrow(() -> new RuntimeException("Visitor not found"));

        if (visitor.getExitTime() != null) {
            throw new RuntimeException("Visitor already exited");
        }

        visitor.setExitTime(LocalDateTime.now());
    }

    private VisitorResponseDto mapToDto(Visitor v) {

        return VisitorResponseDto.builder()
                .visitorId(v.getVisitorId())
                .name(v.getName())
                .phone(v.getPhone())
                .purpose(v.getPurpose())
                .flatNumber(
                        v.getProperty() != null ? v.getProperty().getFlatNumber() : "—"
                )
                .block(
                        v.getProperty() != null ? v.getProperty().getBlock() : "—"
                )
                .entryTime(v.getEntryTime())
                .exitTime(v.getExitTime())
                .build();
    }
}
