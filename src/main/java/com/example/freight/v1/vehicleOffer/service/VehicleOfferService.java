package com.example.freight.v1.vehicleOffer.service;

import com.example.freight.exception.NotFoundException;
import com.example.freight.v1.vehicleOffer.model.entity.LoadingPlace;
import com.example.freight.v1.vehicleOffer.model.entity.Offer;
import com.example.freight.v1.vehicleOffer.model.entity.UnloadingPlace;
import com.example.freight.v1.vehicleOffer.model.offer.VehicleOfferRequest;
import com.example.freight.v1.vehicleOffer.model.teleroute.request.TelerouteRequest;
import com.example.freight.v1.vehicleOffer.model.teleroute.response.TelerouteResponseDto;
import com.example.freight.v1.vehicleOffer.repository.OfferRepository;
import com.example.freight.v1.vehicleOffer.service.teleroute.TelerouteRequestMapper;
import com.example.freight.v1.vehicleOffer.service.teleroute.TelerouteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleOfferService {

    private final TelerouteService telerouteService;
    private final TelerouteRequestMapper telerouteRequestMapper;
    private final OfferRepository offerRepository;

    public VehicleOfferService(final TelerouteService telerouteService,
                               final TelerouteRequestMapper telerouteRequestMapper,
                               final OfferRepository offerRepository) {
        this.telerouteService = telerouteService;
        this.telerouteRequestMapper = telerouteRequestMapper;
        this.offerRepository = offerRepository;
    }

    public void createVehicleOffer(final VehicleOfferRequest vehicleOfferRequest) {
        final TelerouteRequest map = telerouteRequestMapper.map(vehicleOfferRequest);
        final TelerouteResponseDto telerouteResponseDto = telerouteService.createOffer(map);
        Offer build = Offer.builder()
                .telerouteOfferId(telerouteResponseDto.getOfferId())
                .telerouteExternalId(telerouteResponseDto.getExternalId())
                .loadingType(vehicleOfferRequest.loadingBodyType())
                .loadingWeight(vehicleOfferRequest.loadingWeight())
                .loadingLength(vehicleOfferRequest.loadingLength())
                .description(vehicleOfferRequest.description())
                .publishDateTime(telerouteResponseDto.getPublishDateTime())
                .loadingPlace(mapLoadingPlace(vehicleOfferRequest))
                .unloadingPlace(mapUnloadingPlace(vehicleOfferRequest))
                .transeuOfferId(null)
                .timoconOfferId(null)
                .userId(null)
                .build();
        offerRepository.save(build);
    }

    public List<Offer> getOffers() {
        return offerRepository.findAll();
    }

    private UnloadingPlace mapUnloadingPlace(final VehicleOfferRequest vehicleOfferRequest) {
        final VehicleOfferRequest.UnloadingPlace unloadingPlace = vehicleOfferRequest.unloadingPlace();
        return UnloadingPlace.builder()
                .unloadingCity(unloadingPlace.city())
                .unloadingCountry(unloadingPlace.country())
                .unloadingPostalCode(unloadingPlace.postalCode())
                .unloadingDateAndTime(unloadingPlace.unloadingDateAndTime().toString())
                .build();
    }

    private LoadingPlace mapLoadingPlace(final VehicleOfferRequest vehicleOfferRequest) {
        final VehicleOfferRequest.LoadingPlace loadingPlace = vehicleOfferRequest.loadingPlace();
        return LoadingPlace.builder()
                .loadingCity(loadingPlace.city())
                .loadingCountry(loadingPlace.country())
                .loadingPostalCode(loadingPlace.postalCode())
                .loadingDateAndTime(loadingPlace.loadingStartDateAndTime().toString())
                .build();
    }

    public void deleteOffer(final String id) {
        if (offerRepository.existsById(id)) {
            offerRepository.deleteById(id);
        } else {
            throw new NotFoundException("Offer with id " + id + " not found");
        }

    }
}
