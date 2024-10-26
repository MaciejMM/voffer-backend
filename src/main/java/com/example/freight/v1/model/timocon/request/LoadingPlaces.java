package com.example.freight.v1.model.timocon.request;

import lombok.Data;

@Data
public class LoadingPlaces {

    private LoadingType loadingType;
    private LoadingAddress address;
    private String earliestLoadingDate;
    private String latestLoadingDate;
}
