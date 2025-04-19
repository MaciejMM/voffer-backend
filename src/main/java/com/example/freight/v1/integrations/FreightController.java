package com.example.freight.v1.integrations;


import com.example.freight.v1.integrations.freight.FreightRequest;
import com.example.freight.v1.integrations.freight.FreightService;
import com.example.freight.v1.integrations.freight.dto.FreightDto;
import com.example.freight.v1.integrations.freight.entity.Freight;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(path = "api/v1/freight", produces = MediaType.APPLICATION_JSON_VALUE)
public class FreightController {
    private final FreightService freightService;

    public FreightController(final FreightService freightService) {
        this.freightService = freightService;
    }

    @PostMapping
    public ResponseEntity<FreightDto> createFreight(final @RequestBody FreightRequest request, final @RequestHeader Map<String, String> headers) {
        FreightDto freight = freightService.createFreight(request, headers);
        return ResponseEntity.ok().body(freight);
    }

    @GetMapping
    public HttpEntity<List<FreightDto>> getFreight() {
        final List<FreightDto> freights = freightService.getFreights();
        return ResponseEntity.ok().body(freights);
    }

}
