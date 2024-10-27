package com.example.freight.v1.model.transeu.request;

import lombok.Data;

@Data
public class Cargo {
    private Integer capacity;
    private Double height;
    private Double width;
    private Double volume;
}
