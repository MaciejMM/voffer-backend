package com.example.freight.v1.vehicleOffer.model.teleroute.request;

import lombok.Data;

@Data
public class Arrival {
    private Location location;
    private Interval interval;
}