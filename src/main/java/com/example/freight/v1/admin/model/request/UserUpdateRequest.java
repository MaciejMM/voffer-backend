package com.example.freight.v1.admin.model.request;

import jakarta.validation.constraints.NotBlank;


public record UserUpdateRequest(
        @NotBlank String email,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String title
) {
}
