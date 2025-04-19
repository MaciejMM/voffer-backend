package com.example.freight.v1.integrations.offer;

import com.example.freight.auth.TokenServiceMapper;
import com.example.freight.exception.NotFoundException;
import com.example.freight.utlis.AdminUtil;
import com.example.freight.utlis.JsonUtil;
import com.example.freight.v1.vehicleOffer.model.dto.OfferDto;
import com.example.freight.v1.vehicleOffer.model.dto.OfferDtoMapper;
import com.example.freight.v1.integrations.offer.entity.Offer;
import com.example.freight.v1.integrations.offer.entity.OfferHistoryStatus;
import com.example.freight.v1.vehicleOffer.model.offer.EditOfferRequest;
import com.example.freight.v1.vehicleOffer.model.offer.VehicleOfferRequest;
import com.example.freight.v1.integrations.offer.teleroute.response.TelerouteResponse;
import com.example.freight.v1.integrations.offer.teleroute.response.TelerouteResponseDto;
import com.example.freight.v1.integrations.offer.teleroute.TelerouteOfferService;
import com.example.freight.v1.integrations.offer.teleroute.TelerouteService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final HistoryService historyService;
    private final TokenServiceMapper tokenServiceMapper;
    private final TelerouteOfferService telerouteOfferService;
    private final OfferDtoMapper offerDtoMapper;
    private final VehicleOfferMapper vehicleOfferMapper;

    public VehicleOfferService(final TelerouteService telerouteService,
                               final OfferRepository offerRepository,
                               final OfferMapper offerMapper, HistoryService historyService,
                               final TelerouteOfferService telerouteOfferService,
                               final TokenServiceMapper tokenServiceMapper, OfferDtoMapper offerDtoMapper, VehicleOfferMapper vehicleOfferMapper) {
        this.telerouteService = telerouteService;
        this.offerRepository = offerRepository;
        this.offerMapper = offerMapper;
        this.historyService = historyService;
        this.telerouteOfferService = telerouteOfferService;
        this.tokenServiceMapper = tokenServiceMapper;
        this.offerDtoMapper = offerDtoMapper;
        this.vehicleOfferMapper = vehicleOfferMapper;
    }

    @Transactional
    public Offer createVehicleOffer(final VehicleOfferRequest vehicleOfferRequest, final Map<String, String> headers) {
        LOGGER.info("Creating offer: {}", JsonUtil.toJson(vehicleOfferRequest));
        Map<String, String> tokenMap = tokenServiceMapper.map(headers);
        TelerouteResponseDto offer = telerouteOfferService.createOffer(vehicleOfferRequest, tokenMap);

        final Offer build = offerMapper.createOffer(
                offer,
                vehicleOfferRequest,
                AdminUtil.getUserId());
        historyService.save(build, OfferHistoryStatus.CREATED);
        return offerRepository.save(build);
    }

    @Transactional
    public List<OfferDto> getOffers() {
        List<Offer> offerList = offerRepository
                .findAllByUserId(AdminUtil.getUserId())
                .orElseThrow(() -> new NotFoundException("Offers not found"));
        return offerList.stream().map(offerDtoMapper::map).toList();

    }

    @Transactional
    public Offer updateVehicleOffer(final Long id, final Map<String, String> headers) {
        LOGGER.info("updating offer: {}", id);
        Map<String, String> tokenMap = tokenServiceMapper.map(headers);
        Offer offer = offerRepository.findOfferByIdAndUserId(id, AdminUtil.getUserId()).orElseThrow(() -> new NotFoundException("Offer not found"));
        final String telerouteExternalId = offer.getTelerouteExternalId();
        if (telerouteExternalId == null) {
            offerRepository.deleteById(id.toString());
            final VehicleOfferRequest map = vehicleOfferMapper.map(offer);
            final TelerouteResponseDto updatedOffer = telerouteOfferService.createOffer(map, tokenMap);
            final Offer build = offerMapper.createOffer(
                    updatedOffer,
                    map,
                    AdminUtil.getUserId());
            historyService.save(build, OfferHistoryStatus.UPDATED);
            return offerRepository.save(build);
        }

        final TelerouteResponse telerouteResponse = telerouteService.getOffer(telerouteExternalId, tokenMap.get(TELEROUTE_ACCESS_TOKEN));
        final VehicleOfferRequest previousVehicleOffer = vehicleOfferMapper.map(offer);
        TelerouteResponseDto telerouteResponseDto = telerouteOfferService.refreshOffer(previousVehicleOffer, telerouteResponse.content().getExternalId(), tokenMap);
        offer.setTelerouteOfferId(telerouteResponseDto.getOfferId());
        offer.setTelerouteExternalId(telerouteResponseDto.getExternalId());
        offer.setPublishDateTime(telerouteResponseDto.getPublishDateTime());
        historyService.save(offer, OfferHistoryStatus.UPDATED);
        return offerRepository.save(offer);
    }

    public void updateOfferList(final String offers) {
        LOGGER.info("Updating offer list: {}", offers);
    }


    @Transactional
    public Offer editOffer(final EditOfferRequest editOfferRequest, final Map<String, String> headers) {
        LOGGER.info("Editing offer: {}", editOfferRequest.offerId());
        Map<String, String> tokenMap = tokenServiceMapper.map(headers);
        final Offer offer = offerRepository.findOfferByIdAndUserId(editOfferRequest.offerId(), AdminUtil.getUserId()).orElseThrow(() -> new NotFoundException("Offer not found"));

        if (offer.getTelerouteOfferId() == null) {
            offerRepository.deleteById(editOfferRequest.offerId().toString());
            final TelerouteResponseDto updatedOffer = telerouteOfferService.createOffer(editOfferRequest.offer(), tokenMap);
            final Offer build = offerMapper.createOffer(
                    updatedOffer,
                    editOfferRequest.offer(),
                    AdminUtil.getUserId());
            build.setId(editOfferRequest.offerId());
            historyService.save(build, OfferHistoryStatus.UPDATED);
            return offerRepository.save(build);
        }
        final TelerouteResponse telerouteResponse = telerouteService.getOffer(offer.getTelerouteExternalId(), tokenMap.get(TELEROUTE_ACCESS_TOKEN));
        TelerouteResponseDto updatedOffer = telerouteOfferService.refreshOffer(editOfferRequest.offer(), telerouteResponse.content().getExternalId(), tokenMap);
        final Offer build = offerMapper.createOffer(
                updatedOffer,
                editOfferRequest.offer(),
                AdminUtil.getUserId());

        historyService.save(build, OfferHistoryStatus.EDITED);
        build.setId(editOfferRequest.offerId());
        return offerRepository.save(build);
    }


    public void deleteOffer(final Long id, final Map<String, String> headers) {
        LOGGER.info("Deleting offer with id: {}", id);
        Map<String, String> tokenMap = tokenServiceMapper.map(headers);

        final Offer offer = offerRepository.findOfferByIdAndUserId(id, AdminUtil.getUserId()).orElseThrow(() -> new NotFoundException("Offer not found"));
        final String externalId = offer.getTelerouteExternalId();
        historyService.save(offer, OfferHistoryStatus.DELETED);

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

}
