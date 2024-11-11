package com.example.freight.v1.admin.model.request;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserRequest(
        String email,
        String firstName,
        String lastName,
        String title,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
