package com.example.freight.v1.vehicleOffer.model.timocon.request;

import com.example.freight.v1.integrations.offer.teleroute.request.TelerouteCountry;

import java.util.Arrays;

public enum TimoconCountry {
    AD, AE, AF, AL, AM, AT, AZ, BA, BE, BG, BH, BY, CH, CN, CY, CZ, DE, DK, DZ, EE, EG, ER, ES, ET, FI, FO, FR, GB, GE, GI, GR, HR, HU, IE, IL, IN, IQ, IR, IS, IT, JO, KG, KW, KZ, LB, LI, LT, LU, LV, LY, MA, MC, MD, ME, MK, MN, MT, NL, NO, NP, OM, PK, PL, PT, QA, RO, RS, RU, SA, SE, SI, SK, SM, SY, TJ, TM, TN, TR, UA, UZ, VA, YE;

    public static String findCountry(String country) {
        return Arrays.stream(TelerouteCountry.values())
                .filter(c -> c.name().equals(country))
                .findFirst()
                .map(Enum::toString)
                .orElse(null);
    }
}
