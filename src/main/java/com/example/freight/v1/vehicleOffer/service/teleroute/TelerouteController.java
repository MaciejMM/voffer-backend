package com.example.freight.v1.vehicleOffer.service.teleroute;

import com.example.freight.v1.vehicleOffer.model.teleroute.auth.TelerouteCredentials;
import com.example.freight.v1.vehicleOffer.model.teleroute.auth.TokenResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/teleroute")
public class TelerouteController {

    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String REFRESH_TOKEN = "teleroute_refresh_token";
    private static final String ACCESS_TOKEN = "teleroute_access_token";
    private static final String DEFAULT_COOKIE_PATH = "/";
    private final TelerouteTokenService telerouteTokenService;

    public TelerouteController(TelerouteTokenService telerouteTokenService) {
        this.telerouteTokenService = telerouteTokenService;
    }

    @PostMapping("/token")
    public ResponseEntity<Void> getTelerouteToken(final @RequestBody TelerouteCredentials telerouteCredentials, final HttpServletResponse response) {
        TokenResponse tokenResponse = telerouteTokenService.getAccessToken(telerouteCredentials);

        final ResponseCookie accessTokenCookie = responseCookieBuilder(ACCESS_TOKEN, tokenResponse.access_token(), 3600);
        final ResponseCookie refreshTokenCookie = responseCookieBuilder(REFRESH_TOKEN, tokenResponse.refresh_token(), 604800);

        response.addHeader(SET_COOKIE_KEY, accessTokenCookie.toString());
        response.addHeader(SET_COOKIE_KEY, refreshTokenCookie.toString());

        return ResponseEntity.ok().build();

    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Void> refreshAccessToken(final @CookieValue("teleroute_refresh_token") String refreshToken, final HttpServletResponse response) {
        final TokenResponse newTokens = telerouteTokenService.refreshAccessToken(refreshToken);
        final ResponseCookie accessTokenCookie = responseCookieBuilder(ACCESS_TOKEN, newTokens.access_token(), 3600);

        response.addHeader(SET_COOKIE_KEY, accessTokenCookie.toString());
        return ResponseEntity.ok().build();
    }

    private ResponseCookie responseCookieBuilder(
            final String refreshToken,
            final String tokenResponse,
            final int maxAgeSeconds) {
        return ResponseCookie.from(refreshToken, tokenResponse)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path(DEFAULT_COOKIE_PATH)
                .maxAge(maxAgeSeconds)
                .build();
    }
}
