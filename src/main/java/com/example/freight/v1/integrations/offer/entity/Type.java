package com.example.freight.v1.integrations.offer.entity;

import lombok.Getter;

@Getter
public enum Type {

    OFFER("offer"),
    VEHICLE("vehicle");

    private final String value;

    Type(String value) {
        this.value = value;
    }

}
