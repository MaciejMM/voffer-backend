package com.example.freight.v1.openStreetMap;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(path = "api/v1/location", produces = MediaType.APPLICATION_JSON_VALUE)
public class OpenStreetMapController {


    private final OpenStreetMapService openStreetMapService;

    public OpenStreetMapController(OpenStreetMapService openStreetMapService) {
        this.openStreetMapService = openStreetMapService;
    }

    @GetMapping(value = "/search")
    public ResponseEntity<List<OpenStreetMapService.CityInfo>> getCities(@RequestParam String query) {
        List<OpenStreetMapService.CityInfo> cityInfos = openStreetMapService.searchCities(query);
        return ResponseEntity.ok().body(cityInfos);
    }
}
