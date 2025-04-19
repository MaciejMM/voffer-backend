package com.example.freight.v1.integrations.freight.transeu.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {

    private String country;
    @JsonProperty(value = "postal_code")
    private String postalCode;
    private String locality;

}
