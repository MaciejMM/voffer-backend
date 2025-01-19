package com.example.freight.auth.models.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterUserDto {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String title;
    private boolean admin;
    private boolean active;

}
