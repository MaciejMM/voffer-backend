package com.example.freight.v1.model.teleroute.response;

import lombok.Data;

import java.util.List;

@Data
public class ResponsePickup{

    private ResponsePickupLocation location;
    private List<String> regions;
}
