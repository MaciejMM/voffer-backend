package com.example.freight.v1.vehicleOffer.model.dto;

import com.example.freight.v1.vehicleOffer.model.entity.LoadingPlace;
import com.example.freight.v1.vehicleOffer.model.entity.Offer;
import com.example.freight.v1.vehicleOffer.model.entity.UnloadingPlace;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferDtoMapper {

    public OfferDto map(final Offer offer){
                        return OfferDto.builder()
                                .id(offer.getId())
                                .userId(offer.getUserId())
                                .telerouteOfferId(offer.getTelerouteOfferId())
                                .telerouteExternalId(offer.getTelerouteExternalId())
                                .timoconOfferId(offer.getTimoconOfferId())
                                .transeuOfferId(offer.getTranseuOfferId())
                                .publishDateTime(offer.getPublishDateTime())
                                .loadingPlace(mapLoadingPlaceDto(offer.getLoadingPlace()))
                                .unloadingPlace(mapUnloadingPlaceDto(offer.getUnloadingPlace()))
                                .description(offer.getDescription())
                                .loadingType(offer.getLoadingType())
                                .loadingWeight(offer.getLoadingWeight())
                                .loadingLength(offer.getLoadingLength())
                                .loadingVolume(offer.getLoadingVolume())
                                .loadingBodyType(offer.getLoadingBodyType())
                                .goodsType(offer.getGoodsType())
                                .publishSelected(offer.getPublishSelected())
                                .build();
            }


    private LoadingPlaceDto mapLoadingPlaceDto(final LoadingPlace loadingPlace){
        return LoadingPlaceDto.builder()
            .loadingCity(loadingPlace.getLoadingCity())
            .loadingCountry(loadingPlace.getLoadingCountry())
            .loadingPostalCode(loadingPlace.getLoadingPostalCode())
            .loadingStartDateAndTime(loadingPlace.getLoadingStartDateAndTime())
            .loadingEndDateAndTime(loadingPlace.getLoadingEndDateAndTime())
            .build();
    }

    private List<UnloadingPlaceDto> mapUnloadingPlaceDto(final List<UnloadingPlace> unloadingPlace){
        return unloadingPlace.stream()
            .map(unloading -> UnloadingPlaceDto.builder()
                .unloadingCity(unloading.getUnloadingCity())
                .unloadingCountry(unloading.getUnloadingCountry())
                .unloadingPostalCode(unloading.getUnloadingPostalCode())
                .unloadingStartDateAndTime(unloading.getUnloadingStartDateAndTime())
                .unloadingEndDateAndTime(unloading.getUnloadingEndDateAndTime())
                .build())
            .collect(Collectors.toList());
    }

}
