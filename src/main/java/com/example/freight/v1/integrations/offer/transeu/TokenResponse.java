package com.example.freight.v1.integrations.offer.transeu;

public record TokenResponse(
        String access_token,
        String refresh_token,
        String token_type,
        int expires_in,
        String scope,
        String id_token
) {
}
