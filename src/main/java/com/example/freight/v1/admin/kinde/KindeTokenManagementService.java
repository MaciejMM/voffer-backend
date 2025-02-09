package com.example.freight.v1.admin.kinde;

import com.example.freight.exception.ServerResponseException;
import com.example.freight.v1.admin.kinde.model.KindeTokenManagementResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class KindeTokenManagementService {

    private static final String TOKEN_ENDPOINT = "/oauth2/token";
    private static final String CLIENT_ID = "client_id";
    private static final String CLIENT_SECRET = "client_secret";
    private static final String GRANT_TYPE = "grant_type";
    private static final String AUDIENCE = "audience";

    @Value("${kinde.management.url}")
    private String kindeManagementUrl;

    @Value("${kinde.management.client_id}")
    private String kindeManagementClientId;

    @Value("${kinde.management.client_secret}")
    private String kindeManagementClientSecret;

    @Value("${kinde.management.audience}")
    private String kindeManagementAudience;

    private final WebClient webClient;

    public KindeTokenManagementService(final WebClient webClient) {
        this.webClient = webClient;
    }

    public KindeTokenManagementResponse getAccessToken() {

        return webClient.post()
                .uri(kindeManagementUrl + TOKEN_ENDPOINT)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body(BodyInserters.fromFormData(createRequestBody()))
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        Mono.error(new ServerResponseException("Kinde server error"))
                )
                .bodyToMono(KindeTokenManagementResponse.class)
                .block();

    }

    private MultiValueMap<String, String> createRequestBody() {
        MultiValueMap<String, String> bodyParams = new LinkedMultiValueMap<>();
        bodyParams.add(CLIENT_ID, kindeManagementClientId);
        bodyParams.add(CLIENT_SECRET, kindeManagementClientSecret);
        bodyParams.add(GRANT_TYPE, "client_credentials");
        bodyParams.add(AUDIENCE, kindeManagementAudience);
        return bodyParams;
    }

}
