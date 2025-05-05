package com.example.freight.v1.integrations.offer;

import com.example.freight.v1.integrations.freight.entity.Freight;
import com.example.freight.v1.integrations.offer.entity.Offer;
import com.example.freight.v1.integrations.offer.entity.OfferHistory;
import com.example.freight.v1.integrations.offer.entity.OfferHistoryStatus;
import com.example.freight.v1.integrations.offer.entity.Type;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class HistoryService {

    private final OfferHistoryRepository offerHistoryRepository;


    public HistoryService(final OfferHistoryRepository offerHistoryRepository) {
        this.offerHistoryRepository = offerHistoryRepository;
    }


    public void save(final Offer offer, final OfferHistoryStatus offerHistoryStatus) {

        final OfferHistory offerHistory = OfferHistory.builder()
                .userId(offer.getUserId())
                .offerId(offer.getTelerouteOfferId())
                .createdAt(LocalDateTime.parse(offer.getPublishDateTime()))
                .status(offerHistoryStatus)
                .type(Type.OFFER)
                .build();

        offerHistoryRepository.save(offerHistory);

    }

    public void save(final Freight freight, final OfferHistoryStatus offerHistoryStatus) {

        final OfferHistory offerHistory = OfferHistory.builder()
                .userId(freight.getUserId())
                .offerId(freight.getTranseuOfferId())
                .createdAt(LocalDateTime.now())
                .status(offerHistoryStatus)
                .type(Type.VEHICLE)
                .build();

        offerHistoryRepository.save(offerHistory);
    }
}
