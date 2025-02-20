package com.example.freight.v1.vehicleOffer;

import com.example.freight.v1.vehicleOffer.model.dto.OfferDto;
import com.example.freight.v1.vehicleOffer.model.entity.Offer;
import com.example.freight.v1.vehicleOffer.model.offer.EditOfferRequest;
import com.example.freight.v1.vehicleOffer.model.offer.VehicleOfferRequest;
import com.example.freight.v1.vehicleOffer.service.VehicleOfferService;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<Offer> createVehicleOffer(final @RequestBody VehicleOfferRequest vehicleOfferRequest, final HttpServletRequest request) {
        final Offer vehicleOffer = vehicleOfferService.createVehicleOffer(vehicleOfferRequest, request);
        return ResponseEntity.ok().body(vehicleOffer);
    }

    @GetMapping
    public List<OfferDto> getVehicleOffers() {
        return vehicleOfferService.getOffers();
    }

    @PutMapping(value = "/{id}")
    public HttpEntity<Offer> updateVehicleOffer(final @PathVariable Long id,final HttpServletRequest request) {
        final Offer offer = vehicleOfferService.updateVehicleOffer(id, request);
        return ResponseEntity.ok().body(offer);
    }


    @PutMapping()
    public ResponseEntity<Offer> editVehicleOffer(final @RequestBody EditOfferRequest editOfferRequest,
                                                  final HttpServletRequest request) {
        final Offer vehicleOffer = vehicleOfferService.editOffer(editOfferRequest, request);
        return ResponseEntity.ok().body(vehicleOffer);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Map<String, String>> deleteVehicleOffer(final @PathVariable Long id,
                                                                  final HttpServletRequest request) {
        vehicleOfferService.deleteOffer(id, request);
        return ResponseEntity.ok().body(Map.of("Message", "Offer deleted successfully"));
    }
}
