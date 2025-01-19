package com.example.freight.auth.models.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
        private String token;
        private long expiresIn;
        private String refreshToken;
}
