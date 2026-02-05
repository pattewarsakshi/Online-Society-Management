package com.society.management.service.impl;

import com.society.management.dto.GuardDashboardResponseDto;
import com.society.management.dto.VisitorEntryRequestDto;
import com.society.management.dto.VisitorEntryResponseDto;
import com.society.management.entity.*;
import com.society.management.repository.*;
import com.society.management.service.VisitorEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VisitorEntryServiceImpl implements VisitorEntryService {

    private final VisitorEntryRepository visitorEntryRepository;
    private final UserRepository userRepository;
    private final SocietyRepository societyRepository;
    private final PropertyRepository propertyRepository;

    @Override
    public VisitorEntryResponseDto createEntry(
            Long societyId,
            VisitorEntryRequestDto request,
            String guardEmail
    ) {
        User guard = userRepository.findByEmail(guardEmail)
                .orElseThrow(() -> new RuntimeException("Guard not found"));

        Society society = societyRepository.findById(societyId)
                .orElseThrow(() -> new RuntimeException("Society not found"));

        Property property = null;
        if (request.getPropertyId() != null) {
            property = propertyRepository.findById(request.getPropertyId())
                    .orElseThrow(() -> new RuntimeException("Property not found"));
        }

        VisitorEntry entry = VisitorEntry.builder()
                .visitorName(request.getVisitorName())
                .visitorPhone(request.getVisitorPhone())
                .purpose(request.getPurpose())
                .society(society)
                .guard(guard)
                .property(property)
                .entryTime(LocalDateTime.now())
                .build();

        VisitorEntry saved = visitorEntryRepository.save(entry);

        return VisitorEntryResponseDto.builder()
                .visitorEntryId(saved.getVisitorEntryId())
                .visitorName(saved.getVisitorName())
                .visitorPhone(saved.getVisitorPhone())
                .purpose(saved.getPurpose())
                .entryTime(saved.getEntryTime())
                .build();
    }

    @Override
    public void markExit(Long visitorEntryId) {
        VisitorEntry entry = visitorEntryRepository.findById(visitorEntryId)
                .orElseThrow(() -> new RuntimeException("Visitor entry not found"));

        entry.setExitTime(LocalDateTime.now());
        visitorEntryRepository.save(entry);
    }
    
    @Override
    public GuardDashboardResponseDto getGuardDashboard(Long societyId) {

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(23, 59, 59);

        long totalToday = visitorEntryRepository
                .countBySociety_SocietyIdAndEntryTimeBetween(
                        societyId, startOfDay, endOfDay
                );

        long inside = visitorEntryRepository
                .countBySociety_SocietyIdAndExitTimeIsNull(societyId);

        return GuardDashboardResponseDto.builder()
                .totalVisitorsToday(totalToday)
                .visitorsInside(inside)
                .build();
    }

}
