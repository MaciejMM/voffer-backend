package com.example.freight.v1.vehicleOffer.model.teleroute.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TelerouteResponseDto {
    private String offerId;
    private String externalId;
    private String errorMessage;
    private String publishDateTime;
}
