package com.example.freight.v1.vehicleOffer.service;

import com.example.freight.v1.vehicleOffer.model.entity.LoadingPlace;
import com.example.freight.v1.vehicleOffer.model.entity.Offer;
import com.example.freight.v1.vehicleOffer.model.entity.UnloadingPlace;
import com.example.freight.v1.vehicleOffer.model.offer.VehicleOfferRequest;
import com.example.freight.v1.vehicleOffer.model.teleroute.response.TelerouteResponseDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class OfferMapper {

    public Offer createOffer(final TelerouteResponseDto telerouteResponseDto,
                             final VehicleOfferRequest vehicleOfferRequest,
                             final String userId) {
        Offer offer = Offer.builder()
                .telerouteOfferId(Optional.ofNullable(telerouteResponseDto).map(TelerouteResponseDto::getOfferId).orElse(null))
                .telerouteExternalId(Optional.ofNullable(telerouteResponseDto).map(TelerouteResponseDto::getExternalId).orElse(null))
                .goodsType(vehicleOfferRequest.goodsType())
                .loadingBodyType(vehicleOfferRequest.loadingBodyType())
                .loadingWeight(vehicleOfferRequest.loadingWeight())
                .loadingLength(vehicleOfferRequest.loadingLength())
                .loadingVolume(vehicleOfferRequest.loadingVolume())
                .loadingType(vehicleOfferRequest.loadingType().name())
                .description(vehicleOfferRequest.description())
                .publishDateTime(Optional.ofNullable(telerouteResponseDto).map(TelerouteResponseDto::getPublishDateTime).orElse(null))
                .publishSelected(null)
                .loadingPlace(mapLoadingPlace(vehicleOfferRequest))
                .transeuOfferId(null)
                .timoconOfferId(null)
                .unloadingPlace(new ArrayList<>())
                .userId(userId).build();

        vehicleOfferRequest.unloadingPlace()
                .forEach(place -> {
                            UnloadingPlace unloadingPlaceBuild = UnloadingPlace.builder()
                                    .unloadingCity(place.city())
                                    .unloadingCountry(place.country())
                                    .unloadingPostalCode(place.postalCode())
                                    .unloadingStartDateAndTime(place.unloadingStartDateAndTime())
                                    .unloadingEndDateAndTime(place.unloadingEndDateAndTime())
                                    .offer(offer)
                                    .build();
                            offer.getUnloadingPlace().add(unloadingPlaceBuild);
                        }
                );

        return offer;

    }


    private static LoadingPlace mapLoadingPlace(final VehicleOfferRequest vehicleOfferRequest) {
        final VehicleOfferRequest.LoadingPlace loadingPlace = vehicleOfferRequest.loadingPlace();
        return LoadingPlace.builder()
                .loadingCity(loadingPlace.city())
                .loadingCountry(loadingPlace.country())
                .loadingPostalCode(loadingPlace.postalCode())
                .loadingStartDateAndTime(loadingPlace.loadingStartDateAndTime())
                .loadingEndDateAndTime(loadingPlace.loadingEndDateAndTime())
                .build();
    }
}
