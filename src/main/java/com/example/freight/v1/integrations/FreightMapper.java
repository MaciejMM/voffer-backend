package com.example.freight.v1.integrations;

import com.example.freight.utlis.AdminUtil;
import com.example.freight.v1.integrations.freight.FreightRequest;
import com.example.freight.v1.integrations.freight.entity.Freight;
import com.example.freight.v1.integrations.freight.entity.FreightCategory;
import com.example.freight.v1.integrations.freight.entity.FreightLoadingPlace;
import com.example.freight.v1.integrations.freight.entity.FreightUnloadingPlace;
import com.example.freight.v1.integrations.freight.entity.FreightVehicle;
import com.example.freight.v1.integrations.offer.transeu.response.TransEuResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class FreightMapper {

    public Freight map(TransEuResponse transEuResponse, final FreightRequest freightRequest) {

        Freight freight = Freight.builder()
                .description(freightRequest.description())
                .length(freightRequest.length())
                .volume(freightRequest.volume())
                .weight(freightRequest.weight())
                .loadingPlace(mapLoadingPlace(freightRequest))
                .unloadingPlace(mapUnloadingPlace(freightRequest))
                .isFullTruck(freightRequest.isFullTruck())
                .selectedVehicles(new ArrayList<>())
                .selectedCategories(new ArrayList<>())
                .transeuOfferId(transEuResponse.getId())
                .updatedBy(null)
                .userId(AdminUtil.getUserId())
                .build();

        freightRequest.selectedVehicles().forEach(v -> {
            FreightVehicle freightVehicle = FreightVehicle.builder()
                    .name(v)
                    .freight(freight)
                    .build();
            freight.getSelectedVehicles().add(freightVehicle);
        });
        freightRequest.selectedCategories().forEach(c -> {
            FreightCategory freightCategory = FreightCategory.builder()
                    .name(c)
                    .freight(freight)
                    .build();
            freight.getSelectedCategories().add(freightCategory);
        });


        return freight;
    }


    private FreightLoadingPlace mapLoadingPlace(final FreightRequest request) {

        return FreightLoadingPlace.builder()
                .place(request.loadingPlace())
                .country(request.loadingCountry())
                .postalCode(request.loadingPostalCode())
                .startDate(request.loadingStartDate())
                .endDate(request.loadingEndDate())
                .startTime(request.loadingStartTime())
                .endTime(request.loadingEndTime())
                .build();

    }

    private FreightUnloadingPlace mapUnloadingPlace(final FreightRequest request) {

        return FreightUnloadingPlace.builder()
                .place(request.unloadingPlace())
                .country(request.unloadingCountry())
                .postalCode(request.unloadingPostalCode())
                .startDate(request.unloadingStartDate())
                .endDate(request.unloadingEndDate())
                .startTime(request.unloadingStartTime())
                .endTime(request.unloadingEndTime())
                .build();

    }
}
