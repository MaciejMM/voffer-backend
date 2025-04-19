package com.example.freight.v1.integrations.offer.teleroute.response;

import java.util.List;

public record TelerouteResponse(
        TelerouteHeader header,
        List<TelerouteError> errors,
        List<TelerouteWarning> warnings,
        TelerouteContent content
) {

    public record TelerouteHeader(
            String statusCode,
            String timestamp,
            String login,
            String request,
            String version
    ) {
    }

    public record TelerouteWarning(
            String code,
            String message
    ) {
    }

    public record TelerouteError(
            String code,
            String message
    ) {
    }

}
