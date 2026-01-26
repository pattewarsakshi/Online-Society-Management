package com.society.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.society.entity.Flat;
import com.society.entity.Society;
import com.society.exception.ResourceNotFoundException;
import com.society.repository.FlatRepository;
import com.society.repository.SocietyRepository;

@Service
@Transactional
public class FlatServiceimpl implements FlatService {

    private final FlatRepository flatRepo;
    private final SocietyRepository societyRepo;

    public FlatServiceimpl(FlatRepository flatRepo, SocietyRepository societyRepo) {
        this.flatRepo = flatRepo;
        this.societyRepo = societyRepo;
    }

    @Override
    public Flat addFlat(Integer societyId, Flat flat) {

        Society society = societyRepo.findById(societyId)
                .orElseThrow(() -> new ResourceNotFoundException("Society not found"));

        flat.setSociety(society);
        return flatRepo.save(flat);
    }

    @Override
    public List<Flat> getFlatsBySociety(Integer societyId) {
        return flatRepo.findAll()
                .stream()
                .filter(f -> f.getSociety().getSocietyId().equals(societyId))
                .toList();
    }

    @Override
    public Flat getFlatById(Integer flatId) {
        return flatRepo.findById(flatId)
                .orElseThrow(() -> new ResourceNotFoundException("Flat not found"));
    }
}
