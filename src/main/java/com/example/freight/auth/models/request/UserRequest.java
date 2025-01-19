package com.example.freight.auth.models.request;

import com.example.freight.auth.models.entity.ERole;
import lombok.Builder;

@Builder
public record UserRequest(
        String email,
        String password,
        String firstName,
        String lastName,
        String title,
        ERole role
) {}
