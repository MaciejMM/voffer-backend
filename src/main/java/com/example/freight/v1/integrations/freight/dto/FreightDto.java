package com.example.freight.v1.integrations.freight.dto;


import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class FreightDto {

    private Long id;
    private String weight;
    private String length;
    private String volume;
    private String description;
    private FreightLoadingPlaceDto loadingPlace;
    private FreightUnloadingPlaceDto unloadingPlace;
    private List<FreightCategoryDto> selectedCategories;
    private List<FreightVehicleDto> selectedVehicles;
    private Boolean isFullTruck;
    private String publishDateTime;
    private String publishSelected;
    private String telerouteFreightId;
    private String telerouteExternalId;
    private String transeuFreightId;
    private String createdBy;
    private String updatedBy;
    private String createdDate;
    private String updatedDate;
    private String userId;
    private Boolean isSuccess;
    private String message;
}
