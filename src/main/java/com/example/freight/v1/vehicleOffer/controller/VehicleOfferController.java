package com.example.freight.v1.vehicleOffer.controller;

import com.example.freight.v1.vehicleOffer.model.entity.Offer;
import com.example.freight.v1.vehicleOffer.model.offer.VehicleOfferRequest;
import com.example.freight.v1.vehicleOffer.service.VehicleOfferService;
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
    public ResponseEntity<Map<String, String>> createVehicleOffer(final @RequestBody VehicleOfferRequest vehicleOfferRequest) {
        vehicleOfferService.createVehicleOffer(vehicleOfferRequest);
        return ResponseEntity.ok().body(Map.of("Message", "Offer created successfully"));
    }

    @GetMapping
    public List<Offer> getVehicleOffers() {
        return vehicleOfferService.getOffers();
    }

    @PutMapping(value = "/{id}")
    public String updateVehicleOffer(final @PathVariable String id) {
        return "All good. You can see this because you are Authenticated.";
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Map<String, String>> deleteVehicleOffer(final @PathVariable String id) {
        vehicleOfferService.deleteOffer(id);
        return ResponseEntity.ok().body(Map.of("Message", "Offer deleted successfully"));
    }
}
