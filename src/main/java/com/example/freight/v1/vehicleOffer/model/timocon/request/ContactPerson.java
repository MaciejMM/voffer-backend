package com.example.freight.v1.vehicleOffer.model.timocon.request;

import lombok.Data;

import java.util.List;

@Data
public class ContactPerson {
    private String email;
    private String firstName;
    private String lastName;
    private List<String> languages;
    private String title;
}
