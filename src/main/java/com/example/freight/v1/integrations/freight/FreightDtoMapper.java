package com.example.freight.v1.integrations.freight;


import com.example.freight.v1.integrations.freight.dto.FreightCategoryDto;
import com.example.freight.v1.integrations.freight.dto.FreightDto;
import com.example.freight.v1.integrations.freight.dto.FreightLoadingPlaceDto;
import com.example.freight.v1.integrations.freight.dto.FreightUnloadingPlaceDto;
import com.example.freight.v1.integrations.freight.dto.FreightVehicleDto;
import com.example.freight.v1.integrations.freight.entity.Freight;
import com.example.freight.v1.integrations.freight.entity.FreightCategory;
import com.example.freight.v1.integrations.freight.entity.FreightLoadingPlace;
import com.example.freight.v1.integrations.freight.entity.FreightUnloadingPlace;
import com.example.freight.v1.integrations.offer.transeu.response.TransEuResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FreightDtoMapper {

    public FreightDto map(final Freight freight, TransEuResponse transEuResponse){
        return FreightDto.builder()
                .id(freight.getId())
                .weight(freight.getWeight())
                .volume(freight.getVolume())
                .length(freight.getLength())
                .description(freight.getDescription())
                .loadingPlace(mapFreightLoadingPlace(freight.getLoadingPlace()))
                .unloadingPlace(mapFreightUnloadingPlace(freight.getUnloadingPlace()))
                .selectedCategories(mapFreightCategories(freight.getSelectedCategories()))
                .selectedVehicles(freight.getSelectedVehicles().stream()
                        .map(val-> FreightVehicleDto.builder().name(val.getName()).build())
                        .toList())
                .isFullTruck(freight.getIsFullTruck())
                .transeuFreightId(freight.getTranseuOfferId())
                .telerouteFreightId(null)
                .createdBy(freight.getCreatedBy())
                .updatedBy(freight.getUpdatedBy())
                .createdDate(freight.getPublishDateTime())
                .updatedDate(null)
                .isSuccess(transEuResponse.getIsSuccess())
                .message(transEuResponse.getMessage())
                .build();
    }

    public FreightDto map(final Freight freight){
        return FreightDto.builder()
                .id(freight.getId())
                .weight(freight.getWeight())
                .volume(freight.getVolume())
                .length(freight.getLength())
                .description(freight.getDescription())
                .loadingPlace(mapFreightLoadingPlace(freight.getLoadingPlace()))
                .unloadingPlace(mapFreightUnloadingPlace(freight.getUnloadingPlace()))
                .selectedCategories(mapFreightCategories(freight.getSelectedCategories()))
                .selectedVehicles(freight.getSelectedVehicles().stream()
                        .map(val-> FreightVehicleDto.builder().name(val.getName()).build())
                        .toList())
                .isFullTruck(freight.getIsFullTruck())
                .transeuFreightId(freight.getTranseuOfferId())
                .build();
    }



    private List<FreightCategoryDto> mapFreightCategories(final List<FreightCategory> freightCategories) {
        return freightCategories.stream()
                .map(val->FreightCategoryDto.builder().name(val.getName()).build())
                .toList();
    }


    private FreightUnloadingPlaceDto mapFreightUnloadingPlace(FreightUnloadingPlace unloadingPlace) {

        return FreightUnloadingPlaceDto.builder()
                .country(unloadingPlace.getCountry())
                .place(unloadingPlace.getPlace())
                .postalCode(unloadingPlace.getPostalCode())
                .startDate(unloadingPlace.getStartDate())
                .endDate(unloadingPlace.getEndDate())
                .startTime(unloadingPlace.getStartTime())
                .endTime(unloadingPlace.getEndTime())
                .build();
    }

    private FreightLoadingPlaceDto mapFreightLoadingPlace(final FreightLoadingPlace loadingPlace) {

        return FreightLoadingPlaceDto.builder()
                .country(loadingPlace.getCountry())
                .place(loadingPlace.getPlace())
                .postalCode(loadingPlace.getPostalCode())
                .startDate(loadingPlace.getStartDate())
                .endDate(loadingPlace.getEndDate())
                .startTime(loadingPlace.getStartTime())
                .endTime(loadingPlace.getEndTime())
                .build();
    }
}
