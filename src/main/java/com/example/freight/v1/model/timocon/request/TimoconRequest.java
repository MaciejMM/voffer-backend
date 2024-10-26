package com.example.freight.v1.model.timocon.request;


import lombok.Data;

import java.util.List;

@Data
public class TimoconRequest {

    private ContactPerson contactPerson;
    private Customer customer;
    private boolean trackable;
    private VehicleProperties vehicleProperties;
    private boolean acceptQuotes;
    private String freightDescription;
    private Double length_m;
    private List<LoadingPlaces> loadingPlaces;
    private Double weight_t;

}
