package com.society.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.society.entity.ParkingSlot;
import com.society.entityenum.ParkingStatus;
import com.society.entityenum.ParkingType;
import com.society.entityenum.VehicleType;

@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Integer> {

    // 🔹 All parking slots in a society (Admin view)
    List<ParkingSlot> findBySocietySocietyId(Integer societyId);

    // 🔹 All parking slots of a flat (Admin / Resident view)
    List<ParkingSlot> findByFlatFlatId(Integer flatId);

    // 🔹 Find available visitor parking slots (Guard use)
    List<ParkingSlot> findBySocietySocietyIdAndParkingTypeAndStatusAndVehicleType(
            Integer societyId,
            ParkingType parkingType,
            ParkingStatus status,
            VehicleType vehicleType
    );

    // 🔹 Find parking slot assigned to a visitor
    ParkingSlot findByVisitorVisitorId(Integer visitorId);
}
