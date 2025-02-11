package com.example.freight.v1.openStreetMap;

import com.example.freight.utlis.JsonUtil;
import com.example.freight.v1.vehicleOffer.model.teleroute.request.TelerouteCountry;
import com.google.gson.reflect.TypeToken;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
                        .queryParam("countrycodes", getTelerouteCountries())
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
                                    .city(getCity(info))
                                    .countryCode(info.address().countryCode().toUpperCase())
                                    .postalCode(info.address().postcode())
                                    .displayName(info.displayName())
                                    .build();
                        }
                )
                .collect(Collectors.toList());
    }

    private  String getCity(final OpenStreetMapResponse info) {
        return Stream.of(
                info.address().city(),
                info.address().town(),
                info.address().village(),
                info.address().administrative())
                .filter(StringUtils::isNotEmpty)
                .findFirst()
                .orElse("");
    }

    private String getTelerouteCountries() {
        return StringUtils.join(",", TelerouteCountry.getCountryCodes().stream().map(String::toUpperCase).collect(Collectors.toList()));
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
