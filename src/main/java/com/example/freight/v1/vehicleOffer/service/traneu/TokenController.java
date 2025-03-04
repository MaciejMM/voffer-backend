package com.example.freight.v1.vehicleOffer.service.traneu;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/transeu")
public class TokenController {
    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String REFRESH_TOKEN = "transeu_refresh_token";
    private static final String ACCESS_TOKEN = "transeu_access_token";
    private static final String DEFAULT_COOKIE_PATH = "/";

    private final TokenService tokenService;

    public TokenController(final TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/token")
    public ResponseEntity<Void> generateToken(@RequestBody final TokenRequest code, final HttpServletResponse response) {
        TokenResponse token = tokenService.getAccessToken(code.code());

        final ResponseCookie accessTokenCookie = responseCookieBuilder(ACCESS_TOKEN, token.access_token(), 3600);
        final ResponseCookie refreshTokenCookie = responseCookieBuilder(REFRESH_TOKEN, token.refresh_token(), 604800);

        response.addHeader(SET_COOKIE_KEY, accessTokenCookie.toString());
        response.addHeader(SET_COOKIE_KEY, refreshTokenCookie.toString());


        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Void> refreshAccessToken(final @CookieValue(REFRESH_TOKEN) String refreshToken, final HttpServletResponse response) {
        final TokenResponse newTokens = tokenService.refreshAccessToken(refreshToken);
        response.addHeader(SET_COOKIE_KEY, responseCookieBuilder(ACCESS_TOKEN, newTokens.access_token(), 604800).toString());
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
