package com.example.freight.v1.vehicleOffer.model.offer;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record VehicleOfferRequest(
        LoadingPlace loadingPlace,
        UnloadingPlace unloadingPlace,
        String description,
        @NotNull LoadingType loadingType,
        @NotNull String loadingBodyType,
        String goodsType,
        String loadingWeight,
        String loadingLength,
        String loadingVolume,
        String publishSelected
) {
    @Builder
    public record LoadingPlace(
            @NotNull String country,
            @NotNull String postalCode,
            @NotNull String city,
            @NotNull LocalDateTime loadingStartDateAndTime,
            @NotNull LocalDateTime loadingEndDateAndTime
    ) {
    }
    @Builder
    public record UnloadingPlace(
            @NotNull String country,
            @NotNull String postalCode,
            @NotNull String city,
            @NotNull LocalDateTime unloadingStartDateAndTime,
            @NotNull LocalDateTime unloadingEndDateAndTime
    ) {
    }

}
