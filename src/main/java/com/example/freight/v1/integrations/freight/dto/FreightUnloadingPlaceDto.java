package com.example.freight.v1.integrations.freight.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FreightUnloadingPlaceDto {

    private String country;
    private String place;
    private String postalCode;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
}
