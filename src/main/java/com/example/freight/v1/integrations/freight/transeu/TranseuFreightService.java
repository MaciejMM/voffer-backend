package com.example.freight.v1.integrations.freight.transeu;

import com.example.freight.exception.ServerResponseException;
import com.example.freight.utlis.HeaderStatic;
import com.example.freight.v1.BaseService;
import com.example.freight.v1.integrations.freight.FreightRequest;
import com.example.freight.v1.integrations.freight.FreightService;
import com.example.freight.v1.integrations.freight.transeu.request.TranseuFreightRequest;
import com.example.freight.v1.integrations.offer.transeu.response.TransEuResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TranseuFreightService extends BaseService {

    @Value("${integrations.transeu.url}")
    private String transeuUrl;

    @Value("${integrations.transeu.apikey}")
    private String apiKey;


    private final RequestMapper requestMapper;
    private final WebClient webClient;

    public TranseuFreightService(RequestMapper requestMapper) {
        this.requestMapper = requestMapper;
        this.webClient = WebClient.builder().baseUrl(transeuUrl).build();
    }

    public TransEuResponse createFreight(final FreightRequest freightRequest, Map<String, String> tokenMap) {
        final String accessToken = tokenMap.get("transeu_access_token");
        final TranseuFreightRequest body = requestMapper.map(freightRequest);
        String requestBody = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            requestBody = objectMapper.writeValueAsString(body);
            System.out.println("Request Body: " + requestBody);
        } catch (Exception e) {
            e.getMessage();
        }

        return create(requestBody, accessToken);
    }


    private TransEuResponse create(final String body, final String token) {
        try{
            TransEuResponse transEuResponse = webClient.post()
                    .uri(transeuUrl + "/ext/freights-api/v1/freight-exchange")
                    .header(HeaderStatic.AUTHORIZATION, token)
                    .header(HeaderStatic.CONTENT_TYPE_HEADER, HeaderStatic.APPLICATION_JSON_CONTENT_TYPE)
                    .header("Api-key", apiKey)
                    .header("Accept", HeaderStatic.APPLICATION_JSON_CONTENT_TYPE)
                    .bodyValue(body)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, clientResponse -> 
                        clientResponse.bodyToMono(String.class)
                            .flatMap(errorBody -> Mono.error(new ServerResponseException(
                                "API Error: " + clientResponse.statusCode() + " - " + errorBody
                            )))
                    )
                    .bodyToMono(TransEuResponse.class)
                    .block();
            return TransEuResponse.builder()
                    .publishDateTime(LocalDateTime.now().toString())
                    .id(transEuResponse.getId())
                    .isSuccess(true)
                    .message("Offer created successfully")
                    .build();
        }catch (Exception e){
            LOGGER.error("Error creating offer", e);
            String errorMessage;
            if (e instanceof ServerResponseException) {
                errorMessage = e.getMessage();
            } else {
                errorMessage = "Internal error occurred: " + e.getMessage();
            }
            return TransEuResponse.builder()
                    .publishDateTime(LocalDateTime.now().toString())
                    .id(null)
                    .message(errorMessage)
                    .build();
        }
    }


}
