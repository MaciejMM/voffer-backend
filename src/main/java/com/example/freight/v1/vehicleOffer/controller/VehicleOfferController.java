package com.example.freight.v1.vehicleOffer.controller;

import com.example.freight.v1.vehicleOffer.model.entity.Offer;
import com.example.freight.v1.vehicleOffer.model.offer.VehicleOfferRequest;
import com.example.freight.v1.vehicleOffer.service.VehicleOfferService;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(path = "api/v1/vehicle-offers", produces = MediaType.APPLICATION_JSON_VALUE)
public class VehicleOfferController {

    private final VehicleOfferService vehicleOfferService;

    public VehicleOfferController(final VehicleOfferService vehicleOfferService) {
        this.vehicleOfferService = vehicleOfferService;
    }

    @PostMapping
    public ResponseEntity<Offer> createVehicleOffer(final @RequestBody VehicleOfferRequest vehicleOfferRequest, final @CookieValue("teleroute_access_token") String accessToken) {
        final Offer vehicleOffer = vehicleOfferService.createVehicleOffer(vehicleOfferRequest, accessToken);
        return ResponseEntity.ok().body(vehicleOffer);
    }

    @GetMapping
    public List<Offer> getVehicleOffers() {
        return vehicleOfferService.getOffers();
    }

    @PutMapping(value = "/{id}")
    public HttpEntity<Map<String, String>> updateVehicleOffer(final @PathVariable String id) {
        return ResponseEntity.ok().body(Map.of("Message", "Offer updated successfully"));

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Map<String, String>> deleteVehicleOffer(final @PathVariable Long id,
                                                                  final @CookieValue("teleroute_access_token") String accessToken) {
        vehicleOfferService.deleteOffer(id, accessToken);
        return ResponseEntity.ok().body(Map.of("Message", "Offer deleted successfully"));
    }
}
