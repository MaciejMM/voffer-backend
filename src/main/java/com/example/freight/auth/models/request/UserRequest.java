package com.example.freight.auth.models.request;

import com.example.freight.auth.models.entity.ERole;
import jakarta.validation.constraints.NotNull;

public record UserRequest(
        @NotNull String email,
        @NotNull String password,
        @NotNull String firstName,
        @NotNull String lastName,
        @NotNull String title,
        @NotNull ERole role
) {
}
