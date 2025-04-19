package com.example.freight.v1.integrations.offer.teleroute.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TelerouteRequest {
    private Departure departure;
    private Arrival arrival;
    private LoadDescription loadDescription;
    private Owner owner;
}
