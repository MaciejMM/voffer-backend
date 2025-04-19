package com.example.freight.v1.integrations.freight.transeu.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Spots {

    @JsonProperty(value = "spot_order")
    private Integer spotOrder;

    private Place place;
    private List<Operations> operations;

}
