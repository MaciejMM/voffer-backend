package com.example.freight.v1.vehicleOffer.model.teleroute.response;

import lombok.Data;

@Data
public class TelerouteHeader {
    private Integer statusCode;
    private String timestamp;
    private String login;
    private String request;
    private String version;
}
