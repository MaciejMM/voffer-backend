package com.example.freight.exception;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;

public class ExpiredJwtException extends io.jsonwebtoken.ExpiredJwtException {
    public ExpiredJwtException(Header header, Claims claims, String message, Throwable cause) {
        super(header, claims, message, cause);
    }

    public ExpiredJwtException(Header header, Claims claims, String message) {
        super(header, claims, message);
    }

    @Override
    public String getMessage() {
        return "Custom message: " + super.getMessage();
    }
}
