package com.example.freight.v1.vehicleOffer.service.traneu;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {

    @Value("${integrations.transeu.apikey}")
    private String apiKey;
    @Value("${integrations.transeu.client-id}")
    private String clientId;
    @Value("${integrations.transeu.client-secret}")
    private String clientSecret;

    private final static String TRANSEU_ENDPOINT = "/ext/auth-api/accounts/token";

    private final WebClient webClient;

    public TokenService(final WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.platform.trans.eu").build();
    }

    public TokenResponse getAccessToken(final String code) {

        Map<String, String> formData = new HashMap<>();
        formData.put("grant_type", "authorizationCode");
        formData.put("code", code);
        formData.put("redirect_uri", "https://voffer-d18ce4ed1b53.herokuapp.com");
        formData.put("client_id", clientId);
        formData.put("client_secret", clientSecret);

        return webClient.post()
                .uri(TRANSEU_ENDPOINT)
                .header("Api-key", apiKey)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue(formData)
                .retrieve()
                .bodyToMono(TokenResponse.class)
                .block();

    }

    public TokenResponse refreshAccessToken(final String refreshToken) {
        Map<String, String> formData = new HashMap<>();

        formData.put("grant_type", "refreshToken");
        formData.put("refresh_token", refreshToken);
        formData.put("client_id", clientId);
        formData.put("client_secret", clientSecret);

        return webClient.post()
                .uri(TRANSEU_ENDPOINT)
                .header("Api-key", apiKey)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue(formData)
                .retrieve()
                .bodyToMono(TokenResponse.class)
                .block();
    }

}
