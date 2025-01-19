package com.example.freight.v1.vehicleOffer.model.teleroute.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoadDescription {
    private String vehicle;
    private Double weight;
    private Double length;
    private Double volume;
}
