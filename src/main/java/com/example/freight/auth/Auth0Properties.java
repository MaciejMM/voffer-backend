package com.example.freight.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties("auth0")
public record Auth0Properties(
        String audience,
        Client client
) {
    public record Client(
            Map<String, Registration> registration,
            Map<String, Provider> provider
    ) {
        public record Registration(String clientId, String clientSecret) {
        }

        public record Provider(String issuerUri) {
        }
    }
}
