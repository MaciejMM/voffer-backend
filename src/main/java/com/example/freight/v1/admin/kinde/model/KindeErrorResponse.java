package com.example.freight.v1.admin.kinde.model;

import java.util.List;

public record KindeErrorResponse(
        List<Error> errors
) {
    public record Error(
            String code,
            String message
    ) {
    }
}
