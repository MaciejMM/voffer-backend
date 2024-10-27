package com.example.freight.v1.model.teleroute.request;

import lombok.Data;

@Data
public class Departure {
    private Location location;
    private Interval interval;
}