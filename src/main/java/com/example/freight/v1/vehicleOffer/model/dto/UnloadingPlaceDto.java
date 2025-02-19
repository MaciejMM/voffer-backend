package com.example.freight.v1.vehicleOffer.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnloadingPlaceDto {

    private Long unloadingId;
    private String unloadingCountry;
    private String unloadingCity;
    private String unloadingPostalCode;
    private String unloadingStartDateAndTime;
    private String unloadingEndDateAndTime;
    private OfferDto offerId;

}
