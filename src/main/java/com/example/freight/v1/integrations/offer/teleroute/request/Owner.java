package com.example.freight.v1.integrations.offer.teleroute.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Owner {
    private String login;
}