package com.example.freight.v1.integrations.freight.transeu.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Operations {

    @JsonProperty("operation_order")
    private Integer operationOrder;

    private Timespans timespans;
    private String type;
}
