package com.example.freight.v1.vehicleOffer.service.teleroute;

import com.example.freight.exception.ServerResponseException;
import com.example.freight.v1.vehicleOffer.model.teleroute.auth.TelerouteCredentials;
import com.example.freight.v1.vehicleOffer.model.teleroute.auth.TokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TelerouteTokenService {

    @Value("${teleroute.url}")
    private String telerouteUrl;

    @Value("${teleroute.username}")
    private String telerouteUsername;

    @Value("${teleroute.password}")
    private String teleroutePassword;

    private final WebClient webClient;

    private static final String TELEROUTE_URL = "%s/user/token?client_id=freightexchange&client_secret=secret&scope=any&grant_type=password&username=%s&password=%s";
    private static final String TELEROUTE_REFRESH_TOKEN_URL = "%s/user/token?client_id=freightexchange&client_secret=secret&scope=any&grant_type=refresh_token&refresh_token=%s";

    public TelerouteTokenService(final WebClient webClient) {
        this.webClient = webClient;
    }

    public TokenResponse getAccessToken() {
        return webClient.post()
                .uri(String.format(TELEROUTE_URL, telerouteUrl, telerouteUsername, teleroutePassword))
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        Mono.error(new ServerResponseException("Teleroute server error"))
                )
                .bodyToMono(TokenResponse.class)
                .block();
    }

    public TokenResponse getAccessToken(final TelerouteCredentials telerouteCredentials) {
        return webClient.post()
                .uri(String.format(TELEROUTE_URL, telerouteUrl, telerouteCredentials.username(), telerouteCredentials.password()))
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        Mono.error(new ServerResponseException("Teleroute server error"))
                )
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                        Mono.error(new ServerResponseException("Niepoprawny login lub hasło"))
                )
                .bodyToMono(TokenResponse.class)
                .block();
    }

    public TokenResponse refreshAccessToken(final String refreshToken) {
        return webClient.post()
                .uri(String.format(TELEROUTE_REFRESH_TOKEN_URL, telerouteUrl, refreshToken))
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        Mono.error(new ServerResponseException("Teleroute server error"))
                )
                .bodyToMono(TokenResponse.class)
                .block();
    }

}
