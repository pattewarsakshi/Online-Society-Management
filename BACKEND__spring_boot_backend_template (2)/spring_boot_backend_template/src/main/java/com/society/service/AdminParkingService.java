package com.society.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.society.dto.AdminParkingResponseDTO;
import com.society.entity.ParkingSlot;
import com.society.entity.User;
import com.society.entityenum.Role;
import com.society.repository.ParkingSlotRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AdminParkingService {

    private final ParkingSlotRepository parkingSlotRepository;

    public List<AdminParkingResponseDTO> getAllParking(HttpSession session) {
        User admin = getLoggedInAdmin(session);

        return parkingSlotRepository
                .findBySocietySocietyId(admin.getSociety().getSocietyId())
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public List<AdminParkingResponseDTO> getParkingByFlatId(
            Integer flatId,
            HttpSession session) {

        User admin = getLoggedInAdmin(session);

        return parkingSlotRepository
                .findByFlatFlatId(flatId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    private User getLoggedInAdmin(HttpSession session) {
        User user = (User) session.getAttribute("LOGGED_IN_USER");

        if (user == null || user.getRole() != Role.ADMIN) {
            throw new RuntimeException("Admin access only");
        }
        return user;
    }

    private AdminParkingResponseDTO mapToDTO(ParkingSlot slot) {
        return new AdminParkingResponseDTO(
                slot.getParkingId(),
                slot.getSlotNumber(),
                slot.getVehicleType().name(),
                slot.getParkingType().name(),
                slot.getStatus().name()
        );
    }
}

