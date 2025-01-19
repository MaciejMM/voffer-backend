package com.example.freight.v1.vehicleOffer.model.offer;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record VehicleOfferRequest(
        LoadingPlace loadingPlace,
        UnloadingPlace unloadingPlace,
        String description,
        @NotNull LoadingType loadingType,
        @NotNull String loadingBodyType,
        String loadingWeight,
        String loadingLength,
        String loadingVolume,
        String publishSelected,
        ContactDetails contactDetails
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
            @NotNull LocalDateTime unloadingDateAndTime
    ) {
    }
    @Builder
    public record ContactDetails(
            @NotNull String email,
            @NotNull String firstName,
            @NotNull String lastName,
            @NotNull String title,
            List<String> languages
    ) {
    }
}
