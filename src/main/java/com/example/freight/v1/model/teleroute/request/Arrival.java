package com.example.freight.v1.model.teleroute.request;

import lombok.Data;

@Data
public class Arrival {
    private Location location;
    private Interval interval;
}