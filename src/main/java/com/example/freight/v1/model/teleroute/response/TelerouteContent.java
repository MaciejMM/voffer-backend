package com.example.freight.v1.model.teleroute.response;

import com.example.freight.v1.model.teleroute.request.PickUp;
import lombok.Data;

import java.util.List;

@Data
public class TelerouteContent {
    private String offerId;
    private String externalId;
    private Integer paymentDue;
    private ResponsePickup pickup;
    private ResponseDelivery delivery;
    private List<PickUp> pickUps;
    private List<ResponseDelivery> deliveries;
    //TODO: probably rest wont be needed
}
