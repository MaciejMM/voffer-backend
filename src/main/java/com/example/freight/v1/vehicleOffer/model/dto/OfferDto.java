package com.example.freight.v1.vehicleOffer.model.dto;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OfferDto {
    private Long id;
    private String userId;
    private String telerouteOfferId;
    private String telerouteExternalId;
    private String timoconOfferId;
    private String transeuOfferId;
    private String publishDateTime;
    private LoadingPlaceDto loadingPlace;
    private List<UnloadingPlaceDto> unloadingPlace;
    private String description;
    private String loadingType;
    private String loadingWeight;
    private String loadingLength;
    private String loadingVolume;
    private String loadingBodyType;
    private String goodsType;
    private String publishSelected;
}
