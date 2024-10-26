package com.example.freight.v1.model.teleroute.request;

import lombok.Data;

@Data
public class TelerouteAddress {
    private String country;
    private String zip;
    private String city;
}
