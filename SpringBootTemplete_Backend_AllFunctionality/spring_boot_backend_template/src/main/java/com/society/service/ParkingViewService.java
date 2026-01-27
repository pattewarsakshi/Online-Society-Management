package com.society.service;

import org.springframework.stereotype.Service;

import com.society.dto.ParkingDetailsResponseDTO;
import com.society.entity.Flat;
import com.society.repository.FlatRepository;
import com.society.util.ParkingChargeUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParkingViewService {

    private final FlatRepository flatRepository;

    // 🔹 Resident can view parking details
    public ParkingDetailsResponseDTO getParkingDetails(Integer flatId) {

        Flat flat = flatRepository.findById(flatId)
                .orElseThrow(() -> new RuntimeException("Flat not found"));

        ParkingDetailsResponseDTO dto = new ParkingDetailsResponseDTO();
        dto.setFlatId(flat.getFlatId());
        dto.setParkingType(flat.getParkingType().name());
        dto.setParkingCharge(
                ParkingChargeUtil.getParkingCharge(flat.getParkingType())
        );

        return dto;
    }
}
