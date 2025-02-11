package com.example.freight.v1.vehicleOffer.repository;

import com.example.freight.v1.vehicleOffer.model.entity.OfferHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferHistoryRepository extends JpaRepository<OfferHistory, Long> {

}
