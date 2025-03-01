package com.example.freight.v1.vehicleOffer.service.traneu;


import com.example.freight.exception.ServerResponseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
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

    public TokenService(final WebClient webClient) {
        this.webClient = webClient;
    }


    public String getAccessToken(final String code) {

        String response = webClient.post()
                .uri("https://api.platform.trans.eu/ext/auth-api/accounts/token")
                .header("Api-key", apiKey)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters
                        .fromFormData("grant_type", "authorization_code")
                        .with("code", code)
                        .with("redirect_uri", "https://voffer-d18ce4ed1b53.herokuapp.com")
                        .with("client_id", clientId)
                        .with("client_secret", clientSecret)
                )
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new RuntimeException("Client Error: " + clientResponse.statusCode()))
                )
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        Mono.error(new RuntimeException("Server Error: " + clientResponse.statusCode()))
                )
                .bodyToMono(String.class)
                .block();
        return response;

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
