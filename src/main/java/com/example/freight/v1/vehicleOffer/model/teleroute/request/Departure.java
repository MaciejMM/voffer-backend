package com.example.freight.v1.vehicleOffer.model.teleroute.request;

import lombok.Data;

@Data
public class Departure {
    private Location location;
    private Interval interval;
}