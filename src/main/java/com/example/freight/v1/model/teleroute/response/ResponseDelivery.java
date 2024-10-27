package com.example.freight.v1.model.teleroute.response;

import lombok.Data;


@Data
public class ResponseDelivery {

    private ResponseLocation location;
    private ResponseInterval interval;

}
