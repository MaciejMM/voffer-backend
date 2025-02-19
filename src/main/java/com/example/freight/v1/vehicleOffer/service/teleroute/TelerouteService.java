package com.example.freight.v1.vehicleOffer.service.teleroute;

import com.example.freight.utlis.JsonUtil;
import com.example.freight.v1.vehicleOffer.model.teleroute.request.TelerouteRequest;
import com.example.freight.v1.vehicleOffer.model.teleroute.response.TelerouteContent;
import com.example.freight.v1.vehicleOffer.model.teleroute.response.TelerouteResponse;
import com.example.freight.v1.vehicleOffer.model.teleroute.response.TelerouteResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TelerouteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TelerouteService.class);
    private static final String CONTENT_TYPE = "application/json";
    private static final String AUTHORIZATION = "Authorization";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String ACCEPT_VERSION_HEADER = "Accept-Version";
    private static final String DEFAULT_VERSION = "v2";

    @Value("${teleroute.url}")
    private String telerouteUrl;

    private final WebClient webClient;

    public TelerouteService(final WebClient webClient) {
        this.webClient = webClient;
    }

    public TelerouteResponseDto createOffer(final TelerouteRequest telerouteRequest, final String accessToken) {
        try {
            LOGGER.info(telerouteRequest.toString());
            final String telerouteResponse = sendRequest(telerouteRequest, accessToken);
            final TelerouteResponse parsedTelerouteResponse = parseResponse(telerouteResponse);
            return buildResponseDto(parsedTelerouteResponse, null);
        } catch (Exception e) {
            LOGGER.error("Error creating offer: ", e);
            return buildResponseDto(null, e.getMessage());
        }
    }


    public TelerouteResponseDto updateOffer(final TelerouteRequest vehicleOfferRequest, final String externaId, final String accessToken) {
        try {
            final String telerouteResponse = sendUpdateOfferRequest(vehicleOfferRequest, externaId, accessToken);
            final TelerouteResponse parsedTelerouteResponse = parseResponse(telerouteResponse);
            return buildResponseDto(parsedTelerouteResponse, null);
        } catch (Exception e) {
            LOGGER.error("Error updating offer: ", e);
            return buildResponseDto(null, e.getMessage());
        }
    }


    public TelerouteResponse getOffer(final String offerId, final String accessToken) {
        return webClient.get()
                .uri(String.format("%s/vehicle/offers/%s", telerouteUrl, offerId))
                .header(AUTHORIZATION, "Bearer " + accessToken)
                .header(CONTENT_TYPE_HEADER, CONTENT_TYPE)
                .header(ACCEPT_VERSION_HEADER, DEFAULT_VERSION)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError,
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .map(body -> new RuntimeException("Error retrieving offer: " + body)))
                .bodyToMono(TelerouteResponse.class)
                .block();
    }


    public String sendUpdateOfferRequest(final TelerouteRequest vehicleOfferRequest, final String externaId, final String accessToken) {
        return webClient.put()
                .uri(String.format("%s/vehicle/offers/%s", telerouteUrl, externaId))
                .header(AUTHORIZATION, "Bearer " + accessToken)
                .header(ACCEPT_VERSION_HEADER, DEFAULT_VERSION)
                .header(CONTENT_TYPE_HEADER, CONTENT_TYPE)
                .bodyValue(vehicleOfferRequest)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }


    public void deleteOffer(final String offerId, final String accessToken) {
        try {
            webClient.delete()
                    .uri(String.format("%s/vehicle/offers/%s", telerouteUrl, offerId))
                    .header(AUTHORIZATION, "Bearer " + accessToken)
                    .header(CONTENT_TYPE_HEADER, CONTENT_TYPE)
                    .header(ACCEPT_VERSION_HEADER, DEFAULT_VERSION)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            clientResponse -> clientResponse.bodyToMono(String.class)
                                    .map(body -> new RuntimeException("Error deleting offer: " + body)))
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            LOGGER.error("Error deleting offer: ", e);
        }
    }


    private String sendRequest(final TelerouteRequest telerouteRequest, final String accessToken) {
        return webClient.post()
                .uri(String.format("%s/vehicle/offers", telerouteUrl))
                .header(AUTHORIZATION, "Bearer " + accessToken)
                .header(CONTENT_TYPE_HEADER, CONTENT_TYPE)
                .header(ACCEPT_VERSION_HEADER, DEFAULT_VERSION)
                .bodyValue(telerouteRequest)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private TelerouteResponse parseResponse(final String telerouteResponse) {
        return JsonUtil.fromJson(telerouteResponse, TelerouteResponse.class);
    }

    private TelerouteResponseDto buildResponseDto(final TelerouteResponse telerouteResponse, final String errorMessage) {
        final String offerId = Optional.ofNullable(telerouteResponse)
                .map(TelerouteResponse::content)
                .map(TelerouteContent::getOfferId)
                .orElse(null);
        final String externalId = Optional.ofNullable(telerouteResponse)
                .map(TelerouteResponse::content)
                .map(TelerouteContent::getExternalId)
                .orElse(null);

        return TelerouteResponseDto.builder()
                .offerId(offerId)
                .externalId(externalId)
                .errorMessage(errorMessage)
                .publishDateTime(LocalDateTime.now().toString())
                .build();
    }


}
