package com.example.freight.v1.model.teleroute.request;

import lombok.Data;

@Data
public class PickUp {
    private TelerouteLocation location;
    private TelerouteInterval interval;
}
