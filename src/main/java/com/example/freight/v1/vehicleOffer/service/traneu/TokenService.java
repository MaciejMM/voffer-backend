package com.example.freight.v1.vehicleOffer.service.traneu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenService.class);
    private static final String TRANS_EU_TOKEN_URL = "https://api.platform.trans.eu/ext/auth-api/accounts/token";

    @Value("${integrations.transeu.apikey}")
    private String apiKey;
    @Value("${integrations.transeu.client-id}")
    private String clientId;
    @Value("${integrations.transeu.client-secret}")
    private String clientSecret;

    private final RestTemplate restTemplate;

    public TokenService(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public TokenResponse getAccessToken(final String code) {
        final HttpHeaders headers = buildHeaders();
        final String payload = "grant_type=authorization_code&code=" + code + "&redirect_uri=https%3A%2F%2Fvoffer-d18ce4ed1b53.herokuapp.com&client_id=" + clientId + "&client_secret=" + clientSecret;
        final HttpEntity<String> entity = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<TokenResponse> response =
                    restTemplate.exchange(TRANS_EU_TOKEN_URL,
                            HttpMethod.POST,
                            entity,
                            TokenResponse.class);
            return response.getBody();
        } catch (Exception e) {
            LOGGER.error("Error with retrieving access token", e);
            return null;
        }
    }

    public TokenResponse refreshAccessToken(final String refreshToken) {
        final HttpHeaders headers = buildHeaders();
        final String payload = "grant_type=refresh_token&refresh_token=" + refreshToken + "&client_id=" + clientId + "&client_secret=" + clientSecret;
        final HttpEntity<String> entity = new HttpEntity<>(payload, headers);
        try {
            ResponseEntity<TokenResponse> response =
                    restTemplate.exchange(TRANS_EU_TOKEN_URL,
                            HttpMethod.POST,
                            entity,
                            TokenResponse.class);
            return response.getBody();
        } catch (Exception e) {
            LOGGER.error("Error with retrieving refresh token", e);
            return null;
        }
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("Api-key", apiKey);
        return headers;
    }

}
