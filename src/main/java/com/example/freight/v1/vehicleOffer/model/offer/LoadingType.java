package com.example.freight.v1.vehicleOffer.model.offer;

import lombok.Getter;

@Getter
public enum LoadingType {
    FTL,
    LTL;

    public static LoadingType fromString(String loadingType) {
        for (LoadingType type : LoadingType.values()) {
            if (type.name().equalsIgnoreCase(loadingType)) {
                return type;
            }
        }
        return null;
    }
}
