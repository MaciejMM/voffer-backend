package com.example.freight.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class TokenServiceMapper {

    private static final String TELEROUTE_ACCESS_TOKEN = "teleroute_access_token";
    private static final String TELEROUTE_REFRESH_TOKEN = "teleroute_refresh_token";

    public Map<String,String> map(final HttpServletRequest request) {

        Map<String,String> tokenMap = new HashMap<>();
        final String telerouteAccessToken = getTelerouteAccessToken(request);

        tokenMap.put(TELEROUTE_ACCESS_TOKEN, telerouteAccessToken);

        return tokenMap;
    }

    private String getTelerouteAccessToken(final HttpServletRequest request) {
        return Optional.ofNullable(request.getCookies())
                .map(Arrays::asList)
                .orElse(Collections.emptyList())
                .stream()
                .filter(cookie -> TELEROUTE_ACCESS_TOKEN.equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }


}
