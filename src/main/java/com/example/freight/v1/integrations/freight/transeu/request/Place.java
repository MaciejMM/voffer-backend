package com.example.freight.v1.integrations.freight.transeu.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Place {
    private Address address;
}
