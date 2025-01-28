package com.example.freight.v1.vehicleOffer.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class OfferRepositoryTest {


    @Autowired
    private OfferRepository offerRepository;

    @BeforeEach
    public void setUp() {
        offerRepository.deleteAll();
    }

    @Test
    public void findAllByUserId() {
    }

    @Test
    public void findOfferByIdAndUserId() {
    }

    @Test
    public void deleteOfferByTelerouteExternalId() {
    }
}