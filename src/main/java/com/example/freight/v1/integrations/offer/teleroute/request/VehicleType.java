package com.example.freight.v1.integrations.offer.teleroute.request;

import lombok.Getter;

@Getter
public enum VehicleType {

    VAN,
    TAUTLINER,
    BOX,
    OPEN,
    TRAX_WALKING_FLOOR,
    COIL,
    JUMBO,
    MEGATRAILER,
    PUBLIC_WORKS_TIPPERS,
    CEREAL_TIPPER,
    STEEL_TROUGH,
    ARMOURED_TROUGH,
    PALLETABLE_BULK_CARRIERS,
    WALKING_FLOOR,
    LIQUID_TANK,
    PULVERULENT_TANK,
    FLAT,
    LOWLOADER,
    CONTAINER_20,
    CONTAINER_40,
    CONTAINER_45,
    ISOTHERMIC,
    REFRIGERATED,
    FREEZER,
    MULTI_TEMPERATURE;



    public static VehicleType fromString(String value) {
        for (VehicleType vehicleType : VehicleType.values()) {
            if (vehicleType.name().equalsIgnoreCase(value)) {
                return vehicleType;
            }
        }
        return null;
    }

}
