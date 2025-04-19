package com.example.freight.v1.integrations.freight.transeu.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Timespans {
    private String begin;
    private String end;
}
