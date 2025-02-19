package com.example.freight.v1.vehicleOffer.model.offer;

public record EditOfferRequest(
        Long offerId,
        VehicleOfferRequest offer
) {
}
