package com.example.freight.v1.integrations.offer.transeu;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/v1/transeu")
public class TokenController {
    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String REFRESH_TOKEN = "transeu_refresh_token";
    private static final String DEFAULT_COOKIE_PATH = "/";

    private final TokenService tokenService;

    public TokenController(final TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/token")
    public HttpEntity<Map<String, String>> generateToken(@RequestBody final TokenRequest code, final HttpServletResponse response) {
        TokenResponse token = tokenService.getAccessToken(code.code());
        final ResponseCookie refreshTokenCookie = responseCookieBuilder(REFRESH_TOKEN, token.refresh_token(), 604800);
        response.addHeader(SET_COOKIE_KEY, refreshTokenCookie.toString());
        return ResponseEntity.ok().body(Map.of("access_token", token.access_token()));
    }

    @PostMapping("/refresh-token")
    public HttpEntity<Map<String, String>> refreshAccessToken(final @CookieValue(REFRESH_TOKEN) String refreshToken, final HttpServletResponse response) {

        final TokenResponse token = tokenService.refreshAccessToken(refreshToken);
        final ResponseCookie refreshTokenCookie = responseCookieBuilder(REFRESH_TOKEN, token.refresh_token(), 604800);

        response.addHeader(SET_COOKIE_KEY, refreshTokenCookie.toString());

        return ResponseEntity.ok().body(Map.of("access_token", token.access_token()));

    }

    private ResponseCookie responseCookieBuilder(
            final String tokenName,
            final String tokenResponse,
            final int maxAge
    ) {
        return ResponseCookie.from(tokenName, tokenResponse)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path(DEFAULT_COOKIE_PATH)
                .maxAge(maxAge)
                .build();
    }

}
