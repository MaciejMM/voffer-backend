package com.example.freight.v1.vehicleOffer.model.teleroute.auth;


public record TokenResponse (
    String access_token,
    String token_type,
    Integer expires_in,
    String scope,
    String refresh_token
){}
