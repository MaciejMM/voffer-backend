package com.example.freight.v1.model.timocon.request;

import lombok.Data;

import java.util.List;


@Data
public class VehicleProperties {
    private List<String> body;
    private List<String> type;
}
