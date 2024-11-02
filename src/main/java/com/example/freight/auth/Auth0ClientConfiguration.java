package com.example.freight.auth;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(Auth0Properties.class)
public class Auth0ClientConfiguration {

    @Value("${auth0.client_id}")
    private String clientId;

    @Value("${auth0.issuer}")
    private String issuer;

    @Value("${auth0.client_secret}")
    private String clientSecret;


    @Bean
    public Auth0Client coreRockAuth0Client() {
        return new Auth0Client(
                issuer,
                clientId,
                clientSecret
        );
    }

}
