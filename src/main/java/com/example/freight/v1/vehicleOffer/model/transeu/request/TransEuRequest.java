package com.example.freight.v1.vehicleOffer.model.transeu.request;


import lombok.Data;

@Data
public class TransEuRequest {
    private String vehicle_size;
    private String truck_body;
    private LoadingPlace loading_place;
    private UnloadingPlace unloading_places;
    private Cargo cargo;
    private AvailableOn available_on;
}
