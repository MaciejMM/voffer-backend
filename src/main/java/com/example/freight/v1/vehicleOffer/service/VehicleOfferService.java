package com.example.freight.v1.vehicleOffer.service;

import com.example.freight.auth.UserRepository;
import com.example.freight.auth.models.entity.User;
import com.example.freight.exception.NotFoundException;
import com.example.freight.v1.vehicleOffer.model.entity.Offer;
import com.example.freight.v1.vehicleOffer.model.offer.VehicleOfferRequest;
import com.example.freight.v1.vehicleOffer.model.teleroute.request.TelerouteRequest;
import com.example.freight.v1.vehicleOffer.model.teleroute.response.TelerouteResponse;
import com.example.freight.v1.vehicleOffer.model.teleroute.response.TelerouteResponseDto;
import com.example.freight.v1.vehicleOffer.repository.OfferRepository;
import com.example.freight.v1.vehicleOffer.service.teleroute.TelerouteRequestMapper;
import com.example.freight.v1.vehicleOffer.service.teleroute.TelerouteService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleOfferService {

    private final TelerouteService telerouteService;
    private final TelerouteRequestMapper telerouteRequestMapper;
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final OfferMapper offerMapper;

    public VehicleOfferService(final TelerouteService telerouteService,
                               final TelerouteRequestMapper telerouteRequestMapper,
                               final OfferRepository offerRepository,
                               final UserRepository userRepository, OfferMapper offerMapper) {
        this.telerouteService = telerouteService;
        this.telerouteRequestMapper = telerouteRequestMapper;
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
        this.offerMapper = offerMapper;
    }

    public void createVehicleOffer(final VehicleOfferRequest vehicleOfferRequest) {
        final User user = getUser();
        final TelerouteRequest map = telerouteRequestMapper.map(vehicleOfferRequest);
        final TelerouteResponseDto telerouteResponseDto = telerouteService.createOffer(map);
        final Offer build = offerMapper.createOffer(telerouteResponseDto, vehicleOfferRequest, user);
        offerRepository.save(build);
    }

    public List<Offer> getOffers() {
        final User user = getUser();
        return offerRepository
                .findAllByUserId(user.getId().toString())
                .orElseThrow(() -> new NotFoundException("Offers not found"));
    }

    public void updateVehicleOffer(final String id) {
        final User user = getUser();
        TelerouteResponse telerouteResponse = offerRepository.findOfferByIdAndUserId(Long.valueOf(id), user.getId().toString())
                .map(offer -> {
                    final String telerouteExternalId = offer.getTelerouteExternalId();
                    return telerouteService.getOffer(telerouteExternalId);
                })
                .orElseThrow(() -> new NotFoundException("Offer not found"));

    }


    public void deleteOffer(final String id) {
        final User user = getUser();
        final Long ID = Long.valueOf(id);
        final Offer offer = offerRepository.findOfferByIdAndUserId(ID, user.getId().toString()).orElseThrow(() -> new NotFoundException("Offer not found"));
        final String externalId = offer.getTelerouteExternalId();

        if (externalId == null) {
            offerRepository.deleteById(id);
            return;
        }

        final TelerouteResponse telerouteOffer = telerouteService.getOffer(externalId);
        if (telerouteOffer != null) {
            telerouteService.deleteOffer(externalId);
        }
        offerRepository.deleteById(id);
    }

    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}
