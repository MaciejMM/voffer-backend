package com.example.freight.v1.vehicleOffer.service;

import com.example.freight.auth.TokenServiceMapper;
import com.example.freight.exception.NotFoundException;
import com.example.freight.utlis.JsonUtil;
import com.example.freight.v1.vehicleOffer.model.entity.Offer;
import com.example.freight.v1.vehicleOffer.model.entity.OfferHistoryStatus;
import com.example.freight.v1.vehicleOffer.model.offer.VehicleOfferRequest;
import com.example.freight.v1.vehicleOffer.model.teleroute.request.TelerouteRequest;
import com.example.freight.v1.vehicleOffer.model.teleroute.response.TelerouteResponse;
import com.example.freight.v1.vehicleOffer.model.teleroute.response.TelerouteResponseDto;
import com.example.freight.v1.vehicleOffer.repository.OfferRepository;
import com.example.freight.v1.vehicleOffer.service.teleroute.TelerouteOfferService;
import com.example.freight.v1.vehicleOffer.service.teleroute.TelerouteRequestMapper;
import com.example.freight.v1.vehicleOffer.service.teleroute.TelerouteService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class VehicleOfferService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleOfferService.class);

    private final TelerouteService telerouteService;
    private final OfferRepository offerRepository;
    private final OfferMapper offerMapper;
    private final OfferHistoryService offerHistoryService;
    private final TokenServiceMapper tokenServiceMapper;
    private final TelerouteOfferService telerouteOfferService;

    public VehicleOfferService(final TelerouteService telerouteService,
                               final OfferRepository offerRepository,
                               final OfferMapper offerMapper,
                               final OfferHistoryService offerHistoryService,
                               final TelerouteOfferService telerouteOfferService,
                               final TokenServiceMapper tokenServiceMapper
    ) {
        this.telerouteService = telerouteService;
        this.offerRepository = offerRepository;
        this.offerMapper = offerMapper;
        this.offerHistoryService = offerHistoryService;
        this.telerouteOfferService = telerouteOfferService;
        this.tokenServiceMapper = tokenServiceMapper;
    }

    public Offer createVehicleOffer(final VehicleOfferRequest vehicleOfferRequest, final HttpServletRequest accessToken) {
        LOGGER.info("Creating offer: {}", JsonUtil.toJson(vehicleOfferRequest));
        Map<String, String> tokenMap = tokenServiceMapper.map(accessToken);
        TelerouteResponseDto offer = telerouteOfferService.createOffer(vehicleOfferRequest, tokenMap);

        final Offer build = offerMapper.createOffer(
                offer,
                vehicleOfferRequest,
                getUserId());
        offerHistoryService.save(build, OfferHistoryStatus.CREATED);
        return offerRepository.save(build);
    }

    public List<Offer> getOffers() {
        return offerRepository
                .findAllByUserId(getUserId())
                .orElseThrow(() -> new NotFoundException("Offers not found"));
    }

    public void updateVehicleOffer(final String id, final String accessToken) {
        TelerouteResponse telerouteResponse = offerRepository.findOfferByIdAndUserId(Long.valueOf(id), getUserId())
                .map(offer -> {
                    final String telerouteExternalId = offer.getTelerouteExternalId();
                    return telerouteService.getOffer(telerouteExternalId, accessToken);
                })
                .orElseThrow(() -> new NotFoundException("Offer not found"));

    }


    public void deleteOffer(final Long id, final String accessToken) {
        final Offer offer = offerRepository.findOfferByIdAndUserId(id, getUserId()).orElseThrow(() -> new NotFoundException("Offer not found"));
        final String externalId = offer.getTelerouteExternalId();

        if (externalId == null) {
            offerRepository.deleteById(id.toString());
            return;
        }

        final TelerouteResponse telerouteOffer = telerouteService.getOffer(externalId, accessToken);
        if (telerouteOffer != null) {
            telerouteService.deleteOffer(externalId, accessToken);

        }
        offerRepository.deleteById(id.toString());
    }

    private String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return principal.getUsername();
    }

}
