package com.example.freight.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

            private final Auth0Client auth0Client;
            private final Auth0Properties auth0Properties;

    public AuthInterceptor(final Auth0Client auth0Client, final Auth0Properties auth0Properties) {
                this.auth0Client = auth0Client;
                this.auth0Properties = auth0Properties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Fetch the token from Auth0Client
        String token = auth0Client.fetchAccessToken(auth0Properties.audience());

        // Set the Authorization header with the Bearer token
        request.setAttribute("Authorization", "Bearer " + token);

        return true;
    }
}
