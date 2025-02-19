package com.example.freight.v1.vehicleOffer.service;

import com.example.freight.v1.vehicleOffer.model.entity.Offer;
import com.example.freight.v1.vehicleOffer.model.offer.LoadingType;
import com.example.freight.v1.vehicleOffer.model.offer.VehicleOfferRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleOfferMapper {


    public VehicleOfferRequest map(final Offer offer) {
        return VehicleOfferRequest.builder()
                .goodsType(offer.getGoodsType())
                .loadingBodyType(offer.getLoadingBodyType())
                .loadingWeight(offer.getLoadingWeight())
                .loadingLength(offer.getLoadingLength())
                .loadingVolume(offer.getLoadingVolume())
                .loadingType(LoadingType.fromString(offer.getLoadingType()))
                .description(offer.getDescription())
                .publishSelected(offer.getPublishSelected())
                .loadingPlace(mapLoadingPlace(offer))
                .unloadingPlace(mapUnloadingPlace(offer))
                .build();
    }

    private VehicleOfferRequest.LoadingPlace mapLoadingPlace(final Offer offer) {
        return VehicleOfferRequest.LoadingPlace.builder()
                .city(offer.getLoadingPlace().getLoadingCity())
                .country(offer.getLoadingPlace().getLoadingCountry())
                .postalCode(offer.getLoadingPlace().getLoadingPostalCode())
                .loadingStartDateAndTime(offer.getLoadingPlace().getLoadingStartDateAndTime())
                .loadingEndDateAndTime(offer.getLoadingPlace().getLoadingEndDateAndTime())
                .build();
    }

    private List<VehicleOfferRequest.UnloadingPlace> mapUnloadingPlace(final Offer offer) {
        return offer.getUnloadingPlace()
                .stream()
                .map(unloadingPlace -> VehicleOfferRequest.UnloadingPlace.builder()
                        .city(unloadingPlace.getUnloadingCity())
                        .country(unloadingPlace.getUnloadingCountry())
                        .postalCode(unloadingPlace.getUnloadingPostalCode())
                        .unloadingStartDateAndTime(unloadingPlace.getUnloadingStartDateAndTime())
                        .unloadingEndDateAndTime(unloadingPlace.getUnloadingEndDateAndTime())
                        .build())
                .toList();
    }

}
