package com.example.freight.v1.integrations.offer.transeu.request;


import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class TransEuRequest {
    @SerializedName("vehicle_size")
    private String vehicleSize;
    @SerializedName("truck_body")
    private String truckBody;
    @SerializedName("loading_place")
    private LoadingPlace loadingPlace;
    @SerializedName("unloading_places")
    private List<UnloadingPlace> unloadingPlaces;
    private Cargo cargo;
    @SerializedName("available_on")
    private AvailableOn availableOn;
}
