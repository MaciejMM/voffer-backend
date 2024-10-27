package com.example.freight.v1.model.teleroute.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Interval {
    private LocalDateTime start;
    private LocalDateTime end;
}
