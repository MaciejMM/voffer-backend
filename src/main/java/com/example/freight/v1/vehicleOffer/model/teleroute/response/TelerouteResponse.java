package com.example.freight.v1.vehicleOffer.model.teleroute.response;

import java.util.List;

public record TelerouteResponse(
        TelerouteHeader header,
        List<String> errors,
        List<String> warnings,
        TelerouteContent content
) {
}
