package com.example.freight.v1.vehicleOffer.service.traneu;

import com.example.freight.utlis.WebClientFilters;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenService.class);

    @Value("${integrations.transeu.apikey}")
    private String apiKey;
    @Value("${integrations.transeu.client-id}")
    private String clientId;
    @Value("${integrations.transeu.client-secret}")
    private String clientSecret;

    private final static String TRANSEU_TOKEN_ENDPOINT = "/ext/auth-api/accounts/token";

    private WebClient webClient;

    @PostConstruct
    public void init() {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.platform.trans.eu")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .defaultHeader("Api-key", apiKey)
                .filter(logRequest())
                .filter(WebClientFilters.logRequest())
                .filter(WebClientFilters.logResponse())
                .build();
    }


    public String getAccessToken(final String code) {
        String encodedUrl = URLEncoder.encode("https://voffer-d18ce4ed1b53.herokuapp.com/", StandardCharsets.UTF_8);
        String formDataString = """
                grant_type=authorization_code&client_id=%s&client_secret=%s&code=%s&redirect_uri=%s""".formatted(clientId, clientSecret, code, encodedUrl);
        LOGGER.info("Form Data Sent: {}", formDataString);

        try {
            ResponseEntity<String> subscribe = webClient.post()
                    .uri(TRANSEU_TOKEN_ENDPOINT)
                    .body(BodyInserters.fromValue(formDataString))
                    .exchange()
                    .flatMap(respons -> respons.toEntity(String.class))
                    .block();
            LOGGER.info("Subscribed to token response: {}", subscribe.getBody());
            return subscribe.getBody();
        } catch (Exception e) {
            LOGGER.error("Error with retreriving access token", e);
            return null;
        }

    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            LOGGER.debug("Request: {} {}", clientRequest.method(), clientRequest.url());
            clientRequest.headers().forEach((name, values) -> values.forEach(value -> LOGGER.debug("{}={}", name, value)));
            return Mono.just(clientRequest);
        });
    }

    public TokenResponse refreshAccessToken(final String refreshToken) {
        Map<String, String> formData = new HashMap<>();

        formData.put("grant_type", "refreshToken");
        formData.put("refresh_token", refreshToken);
        formData.put("client_id", clientId);
        formData.put("client_secret", clientSecret);

        return webClient.post()
                .uri(TRANSEU_TOKEN_ENDPOINT)
                .header("Api-key", apiKey)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue(formData)
                .retrieve()
                .bodyToMono(TokenResponse.class)
                .block();
    }

}
