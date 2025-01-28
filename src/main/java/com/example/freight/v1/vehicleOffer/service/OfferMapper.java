package com.example.freight.v1.vehicleOffer.service;

import com.example.freight.auth.models.entity.User;
import com.example.freight.v1.vehicleOffer.model.entity.LoadingPlace;
import com.example.freight.v1.vehicleOffer.model.entity.Offer;
import com.example.freight.v1.vehicleOffer.model.entity.UnloadingPlace;
import com.example.freight.v1.vehicleOffer.model.offer.VehicleOfferRequest;
import com.example.freight.v1.vehicleOffer.model.teleroute.response.TelerouteResponseDto;
import org.springframework.stereotype.Service;

@Service
public class OfferMapper  {

    public  Offer createOffer(final TelerouteResponseDto telerouteResponseDto,
                             final VehicleOfferRequest vehicleOfferRequest,
                             final User user) {
        return  Offer.builder()
                .telerouteOfferId(telerouteResponseDto.getOfferId())
                .telerouteExternalId(telerouteResponseDto.getExternalId())
                .goodsType(vehicleOfferRequest.goodsType())
                .loadingBodyType(vehicleOfferRequest.loadingBodyType())
                .loadingWeight(vehicleOfferRequest.loadingWeight())
                .loadingLength(vehicleOfferRequest.loadingLength())
                .loadingVolume(vehicleOfferRequest.loadingVolume())
                .loadingType(vehicleOfferRequest.loadingType().name())
                .description(vehicleOfferRequest.description())
                .publishDateTime(telerouteResponseDto.getPublishDateTime())
                .publishSelected(null)
                .loadingPlace(mapLoadingPlace(vehicleOfferRequest))
                .unloadingPlace(mapUnloadingPlace(vehicleOfferRequest))
                .transeuOfferId(null)
                .timoconOfferId(null)
                .userId(user.getId().toString())
                .build();
    }
    private static UnloadingPlace mapUnloadingPlace(final VehicleOfferRequest vehicleOfferRequest) {
        final VehicleOfferRequest.UnloadingPlace unloadingPlace = vehicleOfferRequest.unloadingPlace();
        return UnloadingPlace.builder()
                .unloadingCity(unloadingPlace.city())
                .unloadingCountry(unloadingPlace.country())
                .unloadingPostalCode(unloadingPlace.postalCode())
                .unloadingDateAndTime(unloadingPlace.unloadingStartDateAndTime().toString())
                .build();
    }

    private static LoadingPlace mapLoadingPlace(final VehicleOfferRequest vehicleOfferRequest) {
        final VehicleOfferRequest.LoadingPlace loadingPlace = vehicleOfferRequest.loadingPlace();
        return LoadingPlace.builder()
                .loadingCity(loadingPlace.city())
                .loadingCountry(loadingPlace.country())
                .loadingPostalCode(loadingPlace.postalCode())
                .loadingDateAndTime(loadingPlace.loadingStartDateAndTime().toString())
                .build();
    }
}
