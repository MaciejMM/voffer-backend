package com.example.freight.utlis;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "/health-check", produces = MediaType.APPLICATION_JSON_VALUE)
public class HealthCheckController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> healthCheck() {
        return ResponseEntity.ok()
                .body(Map.of(
                        "message", "OK")
                );
    }

}
