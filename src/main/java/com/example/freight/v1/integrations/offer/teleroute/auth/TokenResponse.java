package com.example.freight.v1.integrations.offer.teleroute.auth;


public record TokenResponse (
    String access_token,
    String token_type,
    Integer expires_in,
    String scope,
    String refresh_token
){}
