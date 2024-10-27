package com.example.freight.v1.model.teleroute.response;

import lombok.Data;

@Data
public class ResponseInterval {
    private String start;
    private String end;
    private boolean empty;
}
