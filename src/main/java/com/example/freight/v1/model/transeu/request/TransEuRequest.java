package com.example.freight.v1.model.transeu.request;


import lombok.Data;

import java.util.List;

@Data
public class TransEuRequest {
    private String vehicle_size;
    private String truck_body;
    private LoadingPlace loading_place;
    private List<UnloadingPlaces> unloading_places;
    private Cargo cargo;
    private AvailableOn available_on;
}
