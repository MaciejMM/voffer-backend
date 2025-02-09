package com.example.freight.config;


import com.kinde.spring.sdk.KindeSdkClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KindeSdkClientConfig {

    @Bean
    public KindeSdkClient kindeSdkClient() {
        return new KindeSdkClient();
    }
}