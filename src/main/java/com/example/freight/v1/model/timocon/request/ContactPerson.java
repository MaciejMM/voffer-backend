package com.example.freight.v1.model.timocon.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactPerson {
    private String email;
    private String firstName;
    private String lastName;
    private List<String> languages;
    private String title;
}
