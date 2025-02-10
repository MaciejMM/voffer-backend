package com.example.freight.v1.vehicleOffer.service;

import com.example.freight.exception.NotFoundException;
import com.example.freight.utlis.JsonUtil;
import com.example.freight.v1.vehicleOffer.model.entity.Offer;
import com.example.freight.v1.vehicleOffer.model.offer.VehicleOfferRequest;
import com.example.freight.v1.vehicleOffer.model.teleroute.request.TelerouteRequest;
import com.example.freight.v1.vehicleOffer.model.teleroute.response.TelerouteResponse;
import com.example.freight.v1.vehicleOffer.model.teleroute.response.TelerouteResponseDto;
import com.example.freight.v1.vehicleOffer.repository.OfferRepository;
import com.example.freight.v1.vehicleOffer.service.teleroute.TelerouteRequestMapper;
import com.example.freight.v1.vehicleOffer.service.teleroute.TelerouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleOfferService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleOfferService.class);

    private final TelerouteService telerouteService;
    private final TelerouteRequestMapper telerouteRequestMapper;
    private final OfferRepository offerRepository;
    private final OfferMapper offerMapper;

    public VehicleOfferService(final TelerouteService telerouteService,
                               final TelerouteRequestMapper telerouteRequestMapper,
                               final OfferRepository offerRepository, OfferMapper offerMapper) {
        this.telerouteService = telerouteService;
        this.telerouteRequestMapper = telerouteRequestMapper;
        this.offerRepository = offerRepository;
        this.offerMapper = offerMapper;
    }

    public Offer createVehicleOffer(final VehicleOfferRequest vehicleOfferRequest, final String accessToken) {
        LOGGER.info("Creating offer: {}", JsonUtil.toJson(vehicleOfferRequest));
        final TelerouteRequest map = telerouteRequestMapper.map(vehicleOfferRequest);
        final TelerouteResponseDto telerouteResponseDto = telerouteService.createOffer(map, accessToken);
        final Offer build = offerMapper.createOffer(
                telerouteResponseDto,
                vehicleOfferRequest,
                getUserId());
        return offerRepository.save(build);
    }

    public List<Offer> getOffers() {
        return offerRepository
                .findAllByUserId(getUserId())
                .orElseThrow(() -> new NotFoundException("Offers not found"));
    }

    public void updateVehicleOffer(final String id) {
        TelerouteResponse telerouteResponse = offerRepository.findOfferByIdAndUserId(Long.valueOf(id), getUserId())
                .map(offer -> {
                    final String telerouteExternalId = offer.getTelerouteExternalId();
                    return telerouteService.getOffer(telerouteExternalId);
                })
                .orElseThrow(() -> new NotFoundException("Offer not found"));

    }


    public void deleteOffer(final Long id) {
        final Offer offer = offerRepository.findOfferByIdAndUserId(id, getUserId()).orElseThrow(() -> new NotFoundException("Offer not found"));
        final String externalId = offer.getTelerouteExternalId();

        if (externalId == null) {
            offerRepository.deleteById(id.toString());
            return;
        }

        final TelerouteResponse telerouteOffer = telerouteService.getOffer(externalId);
        if (telerouteOffer != null) {
            telerouteService.deleteOffer(externalId);
        }
        offerRepository.deleteById(id.toString());
    }

    private String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return principal.getUsername();
    }
}
