package com.example.freight.utlis;

public enum ExceptionMessage {

    ACCESS_DENIED("Access Denied"),
    ;

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
