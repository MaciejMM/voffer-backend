package com.example.freight.v1.vehicleOffer.model.teleroute.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Interval {
    private String start;
    private String end;
}
