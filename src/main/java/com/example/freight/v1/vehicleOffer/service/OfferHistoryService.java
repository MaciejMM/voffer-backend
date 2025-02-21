package com.example.freight.v1.vehicleOffer.service;

import com.example.freight.v1.vehicleOffer.model.entity.Offer;
import com.example.freight.v1.vehicleOffer.model.entity.OfferHistory;
import com.example.freight.v1.vehicleOffer.model.entity.OfferHistoryStatus;
import com.example.freight.v1.vehicleOffer.repository.OfferHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OfferHistoryService {

    private final OfferHistoryRepository offerHistoryRepository;


    public OfferHistoryService(final OfferHistoryRepository offerHistoryRepository) {
        this.offerHistoryRepository = offerHistoryRepository;
    }


    public void save(final Offer offer, final OfferHistoryStatus offerHistoryStatus) {

        final OfferHistory offerHistory = OfferHistory.builder()
                .userId(offer.getUserId())
                .offerId(offer.getTelerouteOfferId())
                .createdAt(LocalDateTime.parse(offer.getPublishDateTime()))
                .status(offerHistoryStatus)
                .build();

        offerHistoryRepository.save(offerHistory);

    }
}
