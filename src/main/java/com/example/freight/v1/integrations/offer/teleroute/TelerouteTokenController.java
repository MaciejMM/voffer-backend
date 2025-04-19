package com.example.freight.v1.integrations.offer.teleroute;

import com.example.freight.v1.integrations.offer.teleroute.auth.TelerouteCredentials;
import com.example.freight.v1.integrations.offer.teleroute.auth.TokenResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/v1/teleroute")
public class TelerouteTokenController {

    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String REFRESH_TOKEN = "teleroute_refresh_token";
    private static final String ACCESS_TOKEN = "teleroute_access_token";
    private static final String DEFAULT_COOKIE_PATH = "/";
    private static final String ACCESS_TOKEN_KEY = "access_token";
    private final TelerouteTokenService telerouteTokenService;

    public TelerouteTokenController(TelerouteTokenService telerouteTokenService) {
        this.telerouteTokenService = telerouteTokenService;
    }

    @PostMapping("/token")
    public ResponseEntity<Map<String, String>> getTelerouteToken(final @RequestBody TelerouteCredentials telerouteCredentials, final HttpServletResponse response) {
        TokenResponse tokenResponse = telerouteTokenService.getAccessToken(telerouteCredentials);
        final ResponseCookie refreshTokenCookie = responseCookieBuilder(tokenResponse.refresh_token());
        response.addHeader(SET_COOKIE_KEY, refreshTokenCookie.toString());

        return ResponseEntity.ok().body(Map.of(ACCESS_TOKEN_KEY, tokenResponse.access_token()));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Map<String, String>> refreshAccessToken(final @CookieValue("teleroute_refresh_token") String refreshToken,final HttpServletResponse response) {
        final TokenResponse tokenResponse = telerouteTokenService.refreshAccessToken(refreshToken);
        final ResponseCookie refreshTokenCookie = responseCookieBuilder(tokenResponse.refresh_token());
        response.addHeader(SET_COOKIE_KEY, refreshTokenCookie.toString());
        return ResponseEntity.ok().body(Map.of(ACCESS_TOKEN_KEY, tokenResponse.access_token()));
    }


    private ResponseCookie responseCookieBuilder(final String tokenResponse) {
        return ResponseCookie.from(REFRESH_TOKEN, tokenResponse).httpOnly(true).secure(true).sameSite("None").path(DEFAULT_COOKIE_PATH).maxAge(604800).build();
    }
}
