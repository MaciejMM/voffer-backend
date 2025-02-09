package com.example.freight.config;

import com.kinde.KindeClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KindeClientConfig {


    @Value("${kinde.oauth2.domain}")
    private String kindeDomain;

    @Value("${kinde.oauth2.client-id}")
    private String kindeClientId;

    @Value("${kinde.oauth2.client-secret}")
    private String kindeClientSecret;


    public KindeClient buildClient() {
        return com.kinde.KindeClientBuilder
                .builder()
                .domain(kindeDomain)
                .clientId(kindeClientId)
                .clientSecret(kindeClientSecret)
                .build();
    }


}
