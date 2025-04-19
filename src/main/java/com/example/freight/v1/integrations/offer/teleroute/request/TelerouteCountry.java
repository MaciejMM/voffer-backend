package com.example.freight.v1.integrations.offer.teleroute.request;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum TelerouteCountry {
    AF, AD, AL, AT, BA, BE, BG, BY, CH, CY, CZ, DE, DK, EE, ES, FI, FR, GB, GI, GR, HR, HU, IE, IS, IT, LI, LT, LU, LV, MC, MD, ME, MK, MT, NL, NO, PL, PT, RO, RS, RU, SE, SI, SK, SM, TR, UA;

    public static String findCountry(String country) {
        return Arrays.stream(TelerouteCountry.values())
                .filter(c -> c.name().equalsIgnoreCase(country))
                .findFirst()
                .map(Enum::toString)
                .orElse(null);
    }

    public static List<String> getCountryCodes() {
        return Arrays.stream(TelerouteCountry.values())
                .map(Enum::toString)
                .collect(Collectors.toList());
    }
}
