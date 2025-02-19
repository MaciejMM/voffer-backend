package com.example.freight.v1.vehicleOffer.service;

import com.example.freight.auth.TokenServiceMapper;
import com.example.freight.exception.NotFoundException;
import com.example.freight.utlis.JsonUtil;
import com.example.freight.v1.vehicleOffer.model.dto.OfferDto;
import com.example.freight.v1.vehicleOffer.model.dto.OfferDtoMapper;
import com.example.freight.v1.vehicleOffer.model.entity.Offer;
import com.example.freight.v1.vehicleOffer.model.entity.OfferHistoryStatus;
import com.example.freight.v1.vehicleOffer.model.offer.EditOfferRequest;
import com.example.freight.v1.vehicleOffer.model.offer.VehicleOfferRequest;
import com.example.freight.v1.vehicleOffer.model.teleroute.response.TelerouteResponse;
import com.example.freight.v1.vehicleOffer.model.teleroute.response.TelerouteResponseDto;
import com.example.freight.v1.vehicleOffer.repository.OfferRepository;
import com.example.freight.v1.vehicleOffer.service.teleroute.TelerouteOfferService;
import com.example.freight.v1.vehicleOffer.service.teleroute.TelerouteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VehicleOfferService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleOfferService.class);
    public static final String TELEROUTE_ACCESS_TOKEN = "teleroute_access_token";

    private final TelerouteService telerouteService;
    private final OfferRepository offerRepository;
    private final OfferMapper offerMapper;
    private final OfferHistoryService offerHistoryService;
    private final TokenServiceMapper tokenServiceMapper;
    private final TelerouteOfferService telerouteOfferService;
    private final OfferDtoMapper offerDtoMapper;
    private final VehicleOfferMapper vehicleOfferMapper;

    public VehicleOfferService(final TelerouteService telerouteService,
                               final OfferRepository offerRepository,
                               final OfferMapper offerMapper,
                               final OfferHistoryService offerHistoryService,
                               final TelerouteOfferService telerouteOfferService,
                               final TokenServiceMapper tokenServiceMapper, OfferDtoMapper offerDtoMapper, VehicleOfferMapper vehicleOfferMapper) {
        this.telerouteService = telerouteService;
        this.offerRepository = offerRepository;
        this.offerMapper = offerMapper;
        this.offerHistoryService = offerHistoryService;
        this.telerouteOfferService = telerouteOfferService;
        this.tokenServiceMapper = tokenServiceMapper;
        this.offerDtoMapper = offerDtoMapper;
        this.vehicleOfferMapper = vehicleOfferMapper;
    }

    public Offer createVehicleOffer(final VehicleOfferRequest vehicleOfferRequest, final HttpServletRequest request) {
        LOGGER.info("Creating offer: {}", JsonUtil.toJson(vehicleOfferRequest));
        Map<String, String> tokenMap = tokenServiceMapper.map(request);
        TelerouteResponseDto offer = telerouteOfferService.createOffer(vehicleOfferRequest, tokenMap);

        final Offer build = offerMapper.createOffer(
                offer,
                vehicleOfferRequest,
                getUserId());
        offerHistoryService.save(build, OfferHistoryStatus.CREATED);
        return offerRepository.save(build);
    }

    @Transactional
    public List<OfferDto> getOffers() {
        List<Offer> offerList = offerRepository
                .findAllByUserId(getUserId())
                .orElseThrow(() -> new NotFoundException("Offers not found"));
        return offerList.stream().map(offerDtoMapper::map).toList();

    }

    @Transactional
    public Offer updateVehicleOffer(final Long id, final HttpServletRequest request) {
        LOGGER.info("updating offer: {}", id);
        Map<String, String> tokenMap = tokenServiceMapper.map(request);
        Offer offer = offerRepository.findOfferByIdAndUserId(id, getUserId()).orElseThrow(() -> new NotFoundException("Offer not found"));
        final String telerouteExternalId = offer.getTelerouteExternalId();
        if (telerouteExternalId == null) {
            offerRepository.deleteById(id.toString());
            final VehicleOfferRequest map = vehicleOfferMapper.map(offer);
            final TelerouteResponseDto updatedOffer = telerouteOfferService.createOffer(map, tokenMap);
            final Offer build = offerMapper.createOffer(
                    updatedOffer,
                    map,
                    getUserId());
            offerHistoryService.save(build, OfferHistoryStatus.UPDATED);
            return offerRepository.save(build);
        }

        final TelerouteResponse telerouteResponse = telerouteService.getOffer(telerouteExternalId, tokenMap.get(TELEROUTE_ACCESS_TOKEN));
        final VehicleOfferRequest previousVehicleOffer = vehicleOfferMapper.map(offer);
        TelerouteResponseDto telerouteResponseDto = telerouteOfferService.refreshOffer(previousVehicleOffer, telerouteResponse.content().getExternalId(), tokenMap);
        offer.setTelerouteOfferId(telerouteResponseDto.getOfferId());
        offer.setTelerouteExternalId(telerouteResponseDto.getExternalId());
        offer.setPublishDateTime(telerouteResponseDto.getPublishDateTime());
        offerHistoryService.save(offer, OfferHistoryStatus.UPDATED);
        return offerRepository.save(offer);
    }

    @Transactional
    public Offer editOffer(final EditOfferRequest editOfferRequest, final HttpServletRequest request) {
        LOGGER.info("Edditing offer: {}", editOfferRequest.offerId());
        Map<String, String> tokenMap = tokenServiceMapper.map(request);
        final Offer offer = offerRepository.findOfferByIdAndUserId(editOfferRequest.offerId(), getUserId()).orElseThrow(() -> new NotFoundException("Offer not found"));

        if (offer.getTelerouteOfferId() == null) {
            offerRepository.deleteById(editOfferRequest.offerId().toString());
            final TelerouteResponseDto updatedOffer = telerouteOfferService.createOffer(editOfferRequest.offer(), tokenMap);
            final Offer build = offerMapper.createOffer(
                    updatedOffer,
                    editOfferRequest.offer(),
                    getUserId());
            build.setId(editOfferRequest.offerId());
            offerHistoryService.save(build, OfferHistoryStatus.UPDATED);
            return offerRepository.save(build);
        }
        final TelerouteResponse telerouteResponse = telerouteService.getOffer(offer.getTelerouteExternalId(), tokenMap.get(TELEROUTE_ACCESS_TOKEN));
        TelerouteResponseDto updatedOffer = telerouteOfferService.refreshOffer(editOfferRequest.offer(), telerouteResponse.content().getExternalId(), tokenMap);
        final Offer build = offerMapper.createOffer(
                updatedOffer,
                editOfferRequest.offer(),
                getUserId());

        offerHistoryService.save(build, OfferHistoryStatus.EDITED);
        build.setId(editOfferRequest.offerId());
        return offerRepository.save(build);
    }


    public void deleteOffer(final Long id, final HttpServletRequest request) {
        LOGGER.info("Deleting offer with id: {}", id);
        Map<String, String> tokenMap = tokenServiceMapper.map(request);

        final Offer offer = offerRepository.findOfferByIdAndUserId(id, getUserId()).orElseThrow(() -> new NotFoundException("Offer not found"));
        final String externalId = offer.getTelerouteExternalId();
        offerHistoryService.save(offer, OfferHistoryStatus.DELETED);

        if (externalId == null) {
            offerRepository.deleteById(id.toString());
            return;
        }

        final TelerouteResponse telerouteOffer = telerouteService.getOffer(externalId, tokenMap.get(TELEROUTE_ACCESS_TOKEN));
        if (telerouteOffer != null) {
            telerouteService.deleteOffer(externalId, tokenMap.get(TELEROUTE_ACCESS_TOKEN));
        }
        offerRepository.deleteById(id.toString());
    }

    private String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        return principal.getUsername();
    }

}
