package com.society.dto;

import org.springframework.web.bind.annotation.*;

import com.society.entityenum.ParkingType;
import com.society.service.FlatAdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/flats")
@RequiredArgsConstructor
public class AdminFlatController {

    private final FlatAdminService flatAdminService;

    // 🔹 Admin API to assign parking
    @PutMapping("/{flatId}/parking")
    public String updateParking(
            @PathVariable Integer flatId,
            @RequestParam ParkingType parkingType) {

        flatAdminService.updateParking(flatId, parkingType);
        return "Parking updated successfully";
    }
}
