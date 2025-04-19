package com.example.freight.v1.integrations.freight;

import java.util.List;


public record FreightRequest(
        String weight,
        String length,
        String volume,

        String loadingCountry,
        String loadingPostalCode,
        String loadingPlace,
        String loadingStartTime,
        String loadingEndTime,
        String loadingDate,
        String loadingStartDate,
        String loadingEndDate,

        String unloadingCountry,
        String unloadingPostalCode,
        String unloadingPlace,
        String unloadingStartTime,
        String unloadingEndTime,
        String unloadingDate,
        String unloadingStartDate,
        String unloadingEndDate,

        String description,
        List<String> selectedCategories,
        List<String> selectedVehicles,
        Boolean isFullTruck

) {
}
