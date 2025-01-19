package com.example.freight.v1.admin;

import com.example.freight.auth.models.entity.Role;
import jakarta.validation.constraints.NotBlank;


public record UserUpdateRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String title,
        Role role,
        boolean active

) {
}
