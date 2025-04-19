package com.example.freight.v1.integrations.freight.transeu.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Requirements {

    @JsonProperty(value = "is_ftl")
    private Boolean isFtl;

    @JsonProperty(value = "required_truck_bodies")
    private List<String> requiredTruckBodies;

    @JsonProperty(value = "vehicle_size")
    private String vehicleSize;
}
