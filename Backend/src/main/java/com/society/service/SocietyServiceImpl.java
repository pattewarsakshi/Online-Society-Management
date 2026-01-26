package com.society.service;



import org.springframework.stereotype.Service;

import com.society.entity.Society;
import com.society.repository.SocietyRepository;
import com.society.service.SocietyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SocietyServiceImpl implements SocietyService {

    private final SocietyRepository societyRepository;

    @Override
    public Society createSociety(Society society) {

        // 1️⃣ Prevent duplicate society
        if (societyRepository.existsBySocietyName(society.getSocietyName())) {
            throw new RuntimeException("Society already exists");
        }

        // 2️⃣ Save society
        return societyRepository.save(society);
    }

    @Override
    public Society getSocietyById(Integer societyId) {
        return societyRepository.findById(societyId)
                .orElseThrow(() -> new RuntimeException("Society not found"));
    }
}

