package com.example.freight.utlis;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/health-check", produces = MediaType.APPLICATION_JSON_VALUE)
public class HealthCheckController {


    @GetMapping(value = "")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "OK");
        return ResponseEntity.ok(response);
    }
}
