package com.society.service;

import org.springframework.stereotype.Service;

import com.society.entity.Flat;
import com.society.entityenum.ParkingType;
import com.society.repository.FlatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FlatAdminService {

    private final FlatRepository flatRepository;

    // 🔹 Admin assigns parking
    public void updateParking(Integer flatId, ParkingType parkingType) {

        Flat flat = flatRepository.findById(flatId)
                .orElseThrow(() -> new RuntimeException("Flat not found"));

        flat.setParkingType(parkingType);
        flatRepository.save(flat);
    }
}
