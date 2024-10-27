package com.example.freight.v1.model.teleroute.request;

import lombok.Data;

import java.util.List;

@Data
public class TelerouteRequest {
    private PickUp pickUp;
    private TelerouteDelivery delivery;
    private List<PickUp> pickups;
    private List<TelerouteDelivery> deliveries;
    private FreightDescription freightDescription;
    private Owner owner;
}
