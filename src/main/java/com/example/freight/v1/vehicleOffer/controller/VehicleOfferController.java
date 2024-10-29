package com.example.freight.v1.vehicleOffer.controller;

import com.example.freight.v1.model.Message;
import com.example.freight.v1.vehicleOffer.service.VehicleOfferService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "api/v1/vehicle-offers", produces = MediaType.APPLICATION_JSON_VALUE)
public class VehicleOfferController {

    private final VehicleOfferService vehicleOfferService;

    public VehicleOfferController(final VehicleOfferService vehicleOfferService) {
        this.vehicleOfferService = vehicleOfferService;
    }

    @PostMapping
    public void createVehicleOffer() {
        vehicleOfferService.createVehicleOffer();
    }

    @GetMapping(value = "/public")
    public Message getVehicleOffers() {
        return new Message("All good. You DO NOT need to be authenticated to call /api/public.");
    }

    @PutMapping(value = "/private")
    public Message updateVehicleOffer() {
        return new Message("All good. You can see this because you are Authenticated.");
    }

    @DeleteMapping(value = "/private-scoped")
    public Message deleteVehicleOffer() {
        return new Message("All good. You can see this because you are Authenticated with a Token granted the 'read:messages' scope");
    }
}
