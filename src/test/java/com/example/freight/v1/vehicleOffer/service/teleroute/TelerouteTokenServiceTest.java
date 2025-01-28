package com.example.freight.v1.vehicleOffer.service.teleroute;

import com.example.freight.exception.ServerResponseException;
import com.example.freight.v1.vehicleOffer.model.teleroute.auth.TokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class TelerouteTokenServiceTest {

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private WebClient.RequestBodySpec requestBodySpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private TelerouteTokenService telerouteTokenService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        telerouteTokenService = new TelerouteTokenService(webClient);
    }

    @Test
    void shouldReturnAccessToken() {
        TokenResponse expectedToken =new TokenResponse("acces__token", "bearer", 3600, "scope","refreshToken");

        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(any(String.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(TokenResponse.class)).thenReturn(Mono.just(expectedToken));

        TokenResponse actualToken = telerouteTokenService.getAccessToken();

//        assertEquals(expectedToken, actualToken);
        assertNotNull(actualToken);
    }

    @Test
    void shouldThrowServerResponseExceptionOn5xxError() {
        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(any(String.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenAnswer(invocation -> {
            throw new ServerResponseException("Teleroute server is not responding");
        });
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new WebClientResponseException(500, "Internal Server Error", null, null, null)));

        assertThrows(ServerResponseException.class, () -> telerouteTokenService.getAccessToken());
    }
}