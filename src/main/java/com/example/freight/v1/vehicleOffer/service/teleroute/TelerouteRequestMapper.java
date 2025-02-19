package com.example.freight.v1.vehicleOffer.service.teleroute;

import com.example.freight.utlis.DateTimeUtil;
import com.example.freight.v1.vehicleOffer.model.offer.VehicleOfferRequest;
import com.example.freight.v1.vehicleOffer.model.teleroute.request.Address;
import com.example.freight.v1.vehicleOffer.model.teleroute.request.Arrival;
import com.example.freight.v1.vehicleOffer.model.teleroute.request.Departure;
import com.example.freight.v1.vehicleOffer.model.teleroute.request.Interval;
import com.example.freight.v1.vehicleOffer.model.teleroute.request.LoadDescription;
import com.example.freight.v1.vehicleOffer.model.teleroute.request.Location;
import com.example.freight.v1.vehicleOffer.model.teleroute.request.Owner;
import com.example.freight.v1.vehicleOffer.model.teleroute.request.TelerouteCountry;
import com.example.freight.v1.vehicleOffer.model.teleroute.request.TelerouteRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class TelerouteRequestMapper {


    public TelerouteRequest map(final VehicleOfferRequest vehicleOfferRequest, final String telerouteUser) {
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

    private Arrival arrivalMapper(final List<VehicleOfferRequest.UnloadingPlace> unloadingPlace) {
        VehicleOfferRequest.UnloadingPlace unloadingPlace1 = unloadingPlace.stream().findFirst().orElse(null);
        return Arrival
                .builder()
                .location(
                        mapUnloadingLocation(unloadingPlace1)
                )
                .interval(mapInterval(unloadingPlace1.unloadingStartDateAndTime(), unloadingPlace1.unloadingEndDateAndTime()))
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

    private Interval mapInterval(final String start, final String end) {

        return Interval
                .builder()
                .start(DateTimeUtil.formatDateTime(start))
                .end(DateTimeUtil.formatDateTime(end))
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
