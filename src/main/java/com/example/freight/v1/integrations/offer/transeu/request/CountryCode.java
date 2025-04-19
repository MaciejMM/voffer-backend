package com.example.freight.v1.integrations.offer.transeu.request;

import java.util.Arrays;
import java.util.List;

public enum CountryCode {

    AF, AL, DZ, AD, AM, AT, AZ, BY, BE, BA, BG, HR, CY, CZ, DK, EG, EE, FI, FR, GR, DE, GB, HU, IS, IR, IQ, IE, IL, IT, KZ, KG, LV, LI, LT, LU, MK, MT, MA, MD, MC, ME, NL, NO, PK, PL, PT, RO, RU, SM, RS, SK, SI, ES, SE, CH, TJ, TN, TR, TM, UA, UZ;

    public static CountryCode fromString(final String value) {
        return Arrays.stream(CountryCode.values())
                .filter(countryCode -> countryCode.name().equalsIgnoreCase(value))
                .findFirst()
                .orElse(null);

    }

    public static List<CountryCode> asList() {
        return Arrays.asList(CountryCode.values());
    }
}
