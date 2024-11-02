package com.example.freight.auth;

import com.auth0.client.auth.AuthAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.TokenHolder;
import com.auth0.net.TokenRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Auth0Client {
    private final AuthAPI authAPI;
    private final Map<String, TokenHolder> accessTokens = new HashMap<>();

    public Auth0Client(final String domain, final String clientId, final String clientSecret) {
        authAPI = AuthAPI
                .newBuilder(domain, clientId, clientSecret)
                .build();
    }

    public String fetchAccessToken(final String audience) throws Auth0Exception {
        final TokenHolder existingTokenHolder = accessTokens.get(audience);
        final String accessToken;

        if (existingTokenHolder != null && isTokenActive(existingTokenHolder)) {
            accessToken = existingTokenHolder.getAccessToken();
        } else {
            final TokenRequest authRequest = authAPI.requestToken(audience);

            final TokenHolder tokenHolder = authRequest.execute().getBody();
            accessTokens.put(audience, tokenHolder);

            accessToken = tokenHolder.getAccessToken();
        }

        return accessToken;
    }

    private boolean isTokenActive(final TokenHolder tokenHolder) {
        return tokenHolder.getExpiresAt().after(new Date());
    }
}