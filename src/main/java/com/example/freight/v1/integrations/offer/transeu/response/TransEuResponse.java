package com.example.freight.v1.integrations.offer.transeu.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransEuResponse {
    private String id;
    private String errorMessage;
    private String publishDateTime;
}
