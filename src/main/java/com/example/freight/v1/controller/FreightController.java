package com.example.freight.v1.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "api/v1/freight")
public class FreightController {


    @GetMapping
    public void getFreight() {}

    @PostMapping
    public void createFreight() {}

    @PutMapping
    public void updateFreight() {}

    @DeleteMapping
    public void deleteFreight() {}
}
