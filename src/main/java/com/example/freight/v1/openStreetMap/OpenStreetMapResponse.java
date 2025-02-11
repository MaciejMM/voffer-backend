package com.example.freight.v1.openStreetMap;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public record OpenStreetMapResponse(
        @SerializedName("place_id") long placeId,
        String licence,
        String osmType,
        @SerializedName("osm_id") long osmId,
        String lat,
        String lon,
        @SerializedName("class") String placeClass,
        String type,
        @SerializedName("place_rank") int placeRank,
        double importance,
        String addresstype,
        String name,
        @SerializedName("display_name") String displayName,
        AddressDto address,
        @SerializedName("boundingbox") List<String> boundingbox
) {
    public record AddressDto(
            String city,
            String town,
            String village,
            String administrative,
            String municipality,
            String county,
            @SerializedName("state") String state,
            @SerializedName("ISO3166-2-lvl4") String ISO3166Lvl4,
            String postcode,
            String country,
            @SerializedName("country_code") String countryCode
    ) {
    }
}