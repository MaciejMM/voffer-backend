package com.example.freight.v1.admin;

import com.example.freight.auth.models.entity.Role;
import jakarta.validation.constraints.NotEmpty;


public record UserUpdateRequest(
        @NotEmpty String firstName,
        @NotEmpty String lastName,
        @NotEmpty String title,
        Role role,
        boolean active

) {
}
