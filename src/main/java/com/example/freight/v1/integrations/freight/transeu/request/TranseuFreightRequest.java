package com.example.freight.v1.integrations.freight.transeu.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TranseuFreightRequest {

    private Boolean publish;
    private Float capacity;
    private Requirements requirements;
    private List<String> loads;
    private List<Spots> spots;

}
