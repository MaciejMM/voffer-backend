package com.example.freight.exception;

import java.nio.file.AccessDeniedException;

public class AuthorizationException extends AccessDeniedException {

    public AuthorizationException(String msg) {
        super(msg);
    }

}
