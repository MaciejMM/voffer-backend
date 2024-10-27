package com.example.freight.v1.model.teleroute.request;

import lombok.Data;

@Data
public class LoadDescription {
    private String vehicle;
    private double weight;
    private double length;
    private double volume;
}
