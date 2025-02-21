package com.example.freight.v1.vehicleOffer.model.offer;


import java.util.Arrays;

public enum LoadingType {
    FTL,
    LTL;

    public static LoadingType fromString(final String loadingType) {
        return Arrays.stream(LoadingType.values())
                .filter(type -> type.name().equalsIgnoreCase(loadingType))
                .findFirst()
                .orElse(null);
    }
}
