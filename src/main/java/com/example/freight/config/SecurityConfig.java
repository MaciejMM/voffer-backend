package com.example.freight.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/health-check").permitAll()
                        .requestMatchers("/api/v1/**").authenticated()
                        .requestMatchers( "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                )
                .cors(withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(withDefaults())
                )
                .build();
    }

}
