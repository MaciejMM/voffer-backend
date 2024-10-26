package com.example.freight.v1.model.teleroute.response;

import com.example.freight.v1.model.teleroute.request.TelerouteAddress;
import lombok.Data;

import java.util.List;

@Data
public class ResponsePickupLocation {

    private TelerouteAddress address;
    private List<String> regions;

}
