package com.example.freight.v1.vehicleOffer.model.teleroute.request;

import lombok.Data;

@Data
public class TelerouteRequest {
    private Departure departure;
    private Arrival arrival;
    private LoadDescription loadDescription;
    private Owner owner;
}
