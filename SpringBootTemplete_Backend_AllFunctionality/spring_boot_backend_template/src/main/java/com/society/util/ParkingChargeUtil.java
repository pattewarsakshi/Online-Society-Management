package com.society.util;

import com.society.entityenum.ParkingType;

public class ParkingChargeUtil {

    public static double getParkingCharge(ParkingType type) {
        if (type == null) return 0.0;

        return switch (type) {
            case BIKE -> 300.0;
            case CAR -> 700.0;
            default -> 0.0;
        };
    }
}
