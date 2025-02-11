package com.example.freight.v1.vehicleOffer.service.teleroute;

import com.example.freight.v1.vehicleOffer.model.offer.VehicleOfferRequest;
import com.example.freight.v1.vehicleOffer.model.teleroute.request.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.util.Optional.ofNullable;

@Service
public class TelerouteRequestMapper {


    public TelerouteRequest map(final VehicleOfferRequest vehicleOfferRequest,final String telerouteUser) {
        return TelerouteRequest
                .builder()
                .departure(departureMapper(vehicleOfferRequest.loadingPlace()))
                .arrival(arrivalMapper(vehicleOfferRequest.unloadingPlace()))
                .loadDescription(loadDescriptionMapper(vehicleOfferRequest))
                .owner(Owner.builder().login(telerouteUser).build())
                .build();
    }

    private Departure departureMapper(final VehicleOfferRequest.LoadingPlace loadingPlace) {
        return Departure
                .builder()
                .location(mapLoadingLocation(loadingPlace))
                .interval(mapInterval(loadingPlace.loadingStartDateAndTime(), loadingPlace.loadingEndDateAndTime()))
                .build();
    }

    private Location mapLoadingLocation(final VehicleOfferRequest.LoadingPlace loadingPlace) {
        return Location
                .builder()
                .address(
                        Address
                                .builder()
                                .country(TelerouteCountry.findCountry(loadingPlace.country()))
                                .zip(loadingPlace.postalCode())
                                .city(loadingPlace.city())
                                .build()
                )
                .build();
    }

    private Arrival arrivalMapper(final VehicleOfferRequest.UnloadingPlace unloadingPlace) {
        return Arrival
                .builder()
                .location(
                        mapUnloadingLocation(unloadingPlace)
                )
                .interval(mapInterval(unloadingPlace.unloadingStartDateAndTime(), unloadingPlace.unloadingEndDateAndTime()))
                .build();
    }

    private Location mapUnloadingLocation(final VehicleOfferRequest.UnloadingPlace unloadingPlace) {
        return Location
                .builder()
                .address(
                        Address
                                .builder()
                                .country(TelerouteCountry.findCountry(unloadingPlace.country()))
                                .zip(unloadingPlace.postalCode())
                                .city(unloadingPlace.city())
                                .build()
                )
                .build();
    }

    private Interval mapInterval(final LocalDateTime start, final LocalDateTime end) {

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        final String formattedStartDate = start.format(formatter);
        final String formattedEndDate = end.format(formatter);
        return Interval
                .builder()
                .start(formattedStartDate)
                .end(formattedEndDate)
                .build();
    }

    private LoadDescription loadDescriptionMapper(final VehicleOfferRequest vehicleOfferRequest) {
        return LoadDescription
                .builder()
                .vehicle(vehicleOfferRequest.loadingBodyType())
                .weight(ofNullable(vehicleOfferRequest.loadingWeight()).map(Double::parseDouble).orElse(null))
                .length(ofNullable(vehicleOfferRequest.loadingLength()).map(Double::parseDouble).orElse(null))
                .volume(ofNullable(vehicleOfferRequest.loadingVolume()).map(Double::parseDouble).orElse(null))
                .build();
    }

}
