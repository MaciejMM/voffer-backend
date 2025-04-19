package com.example.freight.v1.integrations.freight.transeu;

import com.example.freight.v1.integrations.freight.FreightRequest;
import com.example.freight.v1.integrations.freight.transeu.request.Address;
import com.example.freight.v1.integrations.freight.transeu.request.Operations;
import com.example.freight.v1.integrations.freight.transeu.request.Place;
import com.example.freight.v1.integrations.freight.transeu.request.Requirements;
import com.example.freight.v1.integrations.freight.transeu.request.Spots;
import com.example.freight.v1.integrations.freight.transeu.request.Timespans;
import com.example.freight.v1.integrations.freight.transeu.request.TranseuFreightRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RequestMapper {

    public TranseuFreightRequest map(final FreightRequest freightRequest) {
        List<String> loadsList = new ArrayList<>();
        return TranseuFreightRequest
                .builder()
                .publish(true)
                .capacity(Float.parseFloat(freightRequest.weight()))
                .requirements(Requirements.builder()
                        .isFtl(freightRequest.isFullTruck())
                        .requiredTruckBodies(
                                freightRequest.selectedVehicles()
                        )
                        .vehicleSize(mapSelectedCategories(freightRequest.selectedCategories()))
                        .build())
                .loads(loadsList)
                .spots(List.of(mapLoadingSpot(freightRequest), mapUnloadingSpot(freightRequest)))
                .build();

    }

    private String mapSelectedCategories(List<String> strings) {
        if(strings.size() == 1) return strings.getFirst();

        if(strings.size() > 1) {
            StringBuilder sb = new StringBuilder();
            for (String string : strings) {
                sb.append(string).append("_");
            }
            return sb.substring(0, sb.length() - 1);
        }

        return null;
    }


    private Spots mapLoadingSpot(final FreightRequest request) {
        final Address address = Address.builder()
                .country(request.loadingCountry())
                .locality(request.loadingPlace())
                .postalCode(Optional.ofNullable(request.loadingPostalCode()).orElse("1XXX"))
                .build();
        return Spots.builder()
                .spotOrder(1)
                .place(
                        Place.builder()
                                .address(address)
                                .build()
                ).operations(
                        List.of(mapLoadingOperations(request))
                )
                .build();
    }

    private static Operations mapLoadingOperations(final FreightRequest request) {

        final String beginDate = mapDateAndTime(request.loadingStartDate(), request.loadingStartTime());
        final String endDate = mapDateAndTime(request.loadingEndDate(), request.loadingEndTime());

        return Operations.builder()
                .operationOrder(1)
                .type("loading")
                .timespans(
                        Timespans.builder()
                                .begin(beginDate)
                                .end(endDate)
                                .build()
                )
                .build();
    }

    private Spots mapUnloadingSpot(final FreightRequest request) {

        final Address address = Address.builder()
                .country(request.unloadingCountry())
                .locality(request.unloadingPlace())
                .postalCode(Optional.ofNullable(request.unloadingPostalCode()).orElse("1XXX"))
                .build();
        return Spots.builder()
                .spotOrder(2)
                .place(
                        Place.builder()
                                .address(address)
                                .build()
                ).operations(
                        List.of(mapUnloadingOperations(request))
                )
                .build();
    }

    private static Operations mapUnloadingOperations(FreightRequest request) {

        final String beginDate = mapDateAndTime(request.unloadingStartDate(), request.unloadingStartTime());
        final String endDate = mapDateAndTime(request.unloadingEndDate(), request.unloadingEndTime());

        return Operations.builder()
                .operationOrder(1)
                .type("unloading")
                .timespans(
                        Timespans.builder()
                                .begin(beginDate)
                                .end(endDate)
                                .build()
                )
                .build();
    }

    private static String mapDateAndTime(final String date, final String time) {
        String s = date.split("T")[0];
        return String.format("%sT%s:00+0100", s, time);
    }

}
