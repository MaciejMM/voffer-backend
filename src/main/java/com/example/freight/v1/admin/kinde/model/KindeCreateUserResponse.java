package com.example.freight.v1.admin.kinde.model;

import java.util.List;

public record KindeCreateUserResponse(
        String id,
        boolean created,
        List<Identity> identities
) {
    public record Identity(
            String type,
            Result result
    ) {
        public record Result(
                boolean created
        ) {
        }
    }
}