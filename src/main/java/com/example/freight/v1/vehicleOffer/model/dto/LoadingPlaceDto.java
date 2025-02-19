package com.example.freight.v1.vehicleOffer.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoadingPlaceDto {

    private Long loadingId;

    private String loadingCountry;

    private String loadingCity;

    private String loadingPostalCode;

    private String loadingStartDateAndTime;

    private String loadingEndDateAndTime;

    private OfferDto offerId;
}
