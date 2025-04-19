package com.example.freight.v1.integrations.offer.transeu.request;

import lombok.Data;

@Data
public class TransEuAddress {
    private String locality;
    private String postal_code;
    private String country;
}
