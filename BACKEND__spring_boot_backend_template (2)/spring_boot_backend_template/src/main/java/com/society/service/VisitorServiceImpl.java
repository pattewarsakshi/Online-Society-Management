package com.society.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.society.dto.VisitorEntryDTO;
import com.society.dto.VisitorResponseDTO;
import com.society.entity.Flat;
import com.society.entity.User;
import com.society.entity.Visitor;
import com.society.exception.ResourceNotFoundException;
import com.society.exception.VisitorAlreadyExitedException;
import com.society.repository.FlatRepository;
import com.society.repository.UserRepository;
import com.society.repository.VisitorRepository;
import com.society.service.VisitorService;

@Service
@Transactional
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository visitorRepo;
    private final UserRepository userRepo;
    private final FlatRepository flatRepo;

    public VisitorServiceImpl(
            VisitorRepository visitorRepo,
            UserRepository userRepo,
            FlatRepository flatRepo) {
        this.visitorRepo = visitorRepo;
        this.userRepo = userRepo;
        this.flatRepo = flatRepo;
    }

    @Override
    public VisitorResponseDTO addVisitor(Integer societyId, Integer userId, VisitorEntryDTO dto) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Flat flat = flatRepo.findById(dto.getFlatId())
                .orElseThrow(() -> new ResourceNotFoundException("Flat not found"));

        Visitor visitor = new Visitor();
        visitor.setVisitorName(dto.getVisitorName());
        visitor.setPhone(dto.getPhone());
        visitor.setPurpose(dto.getPurpose());
        visitor.setEntryTime(LocalDateTime.now());
        visitor.setUser(user);
        visitor.setFlat(flat);
        visitor.setSociety(user.getSociety());

        return toDto(visitorRepo.save(visitor));
    }

    @Override
    public VisitorResponseDTO exitVisitor(Integer societyId, Integer visitorId) {

        Visitor visitor = visitorRepo
                .findByVisitorIdAndSociety_SocietyId(visitorId, societyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Visitor not found"));

        if (visitor.getExitTime() != null) {
            throw new VisitorAlreadyExitedException("Visitor already exited");
        }

        visitor.setExitTime(LocalDateTime.now());
        return toDto(visitorRepo.save(visitor));
    }

    @Override
    public List<VisitorResponseDTO> getInsideVisitors(Integer societyId) {
        return visitorRepo
                .findBySociety_SocietyIdAndExitTimeIsNull(societyId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<VisitorResponseDTO> getTodayVisitors(Integer societyId) {

        LocalDateTime start = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime end = LocalDateTime.now().with(LocalTime.MAX);

        return visitorRepo
                .findBySociety_SocietyIdAndEntryTimeBetween(societyId, start, end)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<VisitorResponseDTO> getAllVisitors(Integer societyId) {
        return visitorRepo
                .findBySociety_SocietyId(societyId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // 🔥 FIX FOR LAZY LOADING ERROR
    private VisitorResponseDTO toDto(Visitor v) {

        VisitorResponseDTO dto = new VisitorResponseDTO();
        dto.setVisitorId(v.getVisitorId());
        dto.setVisitorName(v.getVisitorName());
        dto.setPhone(v.getPhone());
        dto.setPurpose(v.getPurpose());
        dto.setEntryTime(v.getEntryTime());
        dto.setExitTime(v.getExitTime());
        dto.setUserId(v.getUser().getUserId());
        dto.setFlatId(v.getFlat().getFlatId());

        return dto;
    }
}
