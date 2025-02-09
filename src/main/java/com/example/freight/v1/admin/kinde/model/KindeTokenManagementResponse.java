package com.example.freight.v1.admin.kinde.model;

public record KindeTokenManagementResponse(
        String access_token,
        String token_type,
        Integer expires_in,
        String scope
) {
}
