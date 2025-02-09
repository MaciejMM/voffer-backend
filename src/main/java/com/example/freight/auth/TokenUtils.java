package com.example.freight.auth;

import com.example.freight.config.KindeClientConfig;
import com.kinde.KindeClient;
import com.kinde.token.KindeToken;
import org.springframework.stereotype.Service;

@Service
public class TokenUtils {

    private static final int STARTING_TOKEN_INDEX = 7;
    private final KindeClientConfig kindeClientConfig;

    public TokenUtils(final KindeClientConfig kindeClientConfig) {
        this.kindeClientConfig = kindeClientConfig;
    }


    public KindeToken decodeToken(String token) {
        final String jwt = token.substring(STARTING_TOKEN_INDEX);
        return kindeClientConfig
                .buildClient()
                .tokenFactory()
                .parse(jwt);
    }

}
