package com.example.freight.v1.integrations.offer;

import com.example.freight.v1.integrations.offer.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, String> {

    Optional<List<Offer>> findAllByUserId(String userId);

    Optional<Offer> findOfferByIdAndUserId(Long id, String userId);

}
