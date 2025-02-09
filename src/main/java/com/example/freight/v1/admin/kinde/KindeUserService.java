package com.example.freight.v1.admin.kinde;

import com.example.freight.exception.ApiRequestException;
import com.example.freight.v1.admin.kinde.model.KindeSuccessResponse;
import com.example.freight.v1.admin.kinde.model.request.KindeCreateUserRequest;
import com.example.freight.v1.admin.kinde.model.KindeCreateUserResponse;
import com.example.freight.v1.admin.kinde.model.KindeTokenManagementResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class KindeUserService {

    @Value("${kinde.management.url}")
    private String kindeManagementUrl;

    private static final String USER_ENDPOINT = "/api/v1/user";

    private final KindeTokenManagementService tokenManagementService;
    private final WebClient webClient;

    public KindeUserService(final KindeTokenManagementService tokenManagementService, final WebClient webClient) {
        this.tokenManagementService = tokenManagementService;
        this.webClient = webClient;
    }



    public KindeCreateUserResponse createUser(KindeCreateUserRequest request) {

        KindeTokenManagementResponse accessToken = getAccessToken();
        try {
            return webClient.post()
                    .uri(kindeManagementUrl + USER_ENDPOINT)
                    .bodyValue(request)
                    .header("Authorization", "Bearer " + accessToken.access_token())
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                System.out.println("HTTP Status: " + response.statusCode());
                                return response.bodyToMono(String.class)
                                        .doOnNext(errorBody -> System.out.println("Error Response: " + errorBody))
                                        .flatMap(errorBody -> Mono.error(new RuntimeException("API call failed: " + errorBody)));
                            }
                    )
                    .bodyToMono(KindeCreateUserResponse.class)
                    .block();
        }  catch (Exception e) {
            throw new ApiRequestException(e.getLocalizedMessage());
        }
    }


    public KindeSuccessResponse deleteUser(final String kindeUserId) {
        KindeTokenManagementResponse accessToken = getAccessToken();
        try {
            return webClient.delete()
                    .uri(kindeManagementUrl + USER_ENDPOINT+"?id="+kindeUserId)
                    .header("Authorization", "Bearer " + accessToken.access_token())
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> {
                                System.out.println("HTTP Status: " + response.statusCode());
                                return response.bodyToMono(String.class)
                                        .doOnNext(errorBody -> System.out.println("Error Response: " + errorBody))
                                        .flatMap(errorBody -> Mono.error(new RuntimeException("API call failed: " + errorBody)));
                            }
                    )
                    .bodyToMono(KindeSuccessResponse.class)
                    .block();
        }  catch (Exception e) {
            throw new ApiRequestException(e.getLocalizedMessage());
        }
    }

    private KindeTokenManagementResponse getAccessToken() {
        return tokenManagementService.getAccessToken();
    }
}
