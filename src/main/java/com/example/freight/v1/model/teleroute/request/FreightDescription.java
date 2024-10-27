package com.example.freight.v1.model.teleroute.request;

import lombok.Data;

@Data
public class FreightDescription {
    private String type;
    private Double netWeight;
    private Double length;
}
