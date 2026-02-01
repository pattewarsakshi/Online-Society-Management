package com.society.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(readOnly = true)
    public ParkingDetailsResponseDTO getParkingDetails(Integer flatId) {

        Flat flat = flatRepository.findFlatWithParking(flatId)
                .orElseThrow(() -> new RuntimeException("Flat not found"));

        ParkingDetailsResponseDTO dto = new ParkingDetailsResponseDTO();
        dto.setFlatId(flat.getFlatId());

        // 🔹 No parking slots
        if (flat.getParkingSlots() == null || flat.getParkingSlots().isEmpty()) {
            dto.setParkingSlots(List.of()); // empty list
            return dto;
        }

        // 🔹 Map ALL parking slots
        List<ParkingDetailsResponseDTO.ParkingSlotInfoDTO> slots =
                flat.getParkingSlots().stream()
                        .map(slot -> {
                            ParkingDetailsResponseDTO.ParkingSlotInfoDTO s =
                                    new ParkingDetailsResponseDTO.ParkingSlotInfoDTO();

                            s.setParkingType(slot.getVehicleType().name());
                            s.setParkingCharge(
                                    ParkingChargeUtil.getParkingCharge(slot.getVehicleType())
                            );

                            return s;
                        })
                        .toList();

        dto.setParkingSlots(slots);
        return dto;
    }

}

