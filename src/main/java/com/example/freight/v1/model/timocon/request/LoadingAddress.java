package com.example.freight.v1.model.timocon.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoadingAddress {
    private String objectType;
    private String city;
    private String country;
    private String postalCode;
}
