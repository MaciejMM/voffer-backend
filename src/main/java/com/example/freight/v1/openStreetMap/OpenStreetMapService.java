package com.example.freight.v1.openStreetMap;

import com.example.freight.utlis.JsonUtil;
import com.google.gson.reflect.TypeToken;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OpenStreetMapService {

    private static final String NOMINATIM_BASE_URL = "https://nominatim.openstreetmap.org/";

    private final WebClient webClient;

    public OpenStreetMapService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(NOMINATIM_BASE_URL).build();
    }

    public List<CityInfo> searchCities(String query) {
        String response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("search")
                        .queryParam("q", query)
                        .queryParam("format", "json")
                        .queryParam("countrycodes","pl,es,uk,fr,de")
                        .queryParam("addressdetails", "1")
                        .build())
                .header("User-Agent", "YourAppName/1.0")
                .retrieve()
                .bodyToMono(
                        String.class)
                .block();

        return parseCityInfo(response);
    }

    private List<CityInfo> parseCityInfo(String response) {
        final Type listType = new TypeToken<List<OpenStreetMapResponse>>() {
        }.getType();
        final List<OpenStreetMapResponse> openStreetMapResponse = JsonUtil.fromJson(response, listType);

        return Optional.ofNullable(openStreetMapResponse)
                .filter(list -> !list.isEmpty())
                .map(this::mapCityInfo)
                .orElse(Collections.emptyList());

    }

    private List<CityInfo> mapCityInfo(List<OpenStreetMapResponse> openStreetMapResponse) {
        return openStreetMapResponse.stream()
                .map(info -> {
                            return CityInfo
                                    .builder()
                                    .city(info.name())
                                    .countryCode(info.address().countryCode().toUpperCase())
                                    .postalCode(info.address().postcode())
                                    .displayName(info.displayName())
                                    .build();
                        }
                )
                .collect(Collectors.toList());
    }

    @Data
    @Builder
    public static class CityInfo {
        private final String city;
        private final String postalCode;
        private final String countryCode;
        private final String displayName;
    }
}
