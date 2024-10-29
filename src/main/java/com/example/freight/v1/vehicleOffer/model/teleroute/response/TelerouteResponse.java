package com.example.freight.v1.vehicleOffer.model.teleroute.response;

import lombok.Data;

import java.util.List;

@Data
public class TelerouteResponse {
    private TelerouteHeader header;
    private List<String> errors;
    private List<String> warnings;
    private TelerouteContent content;
}
