package com.example.freight.v1.vehicleOffer.model.teleroute.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Owner {
    private String login;
}