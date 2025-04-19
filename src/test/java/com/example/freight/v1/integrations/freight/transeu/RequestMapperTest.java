package com.example.freight.v1.integrations.freight.transeu;

import com.example.freight.v1.integrations.freight.FreightRequest;
import com.example.freight.v1.integrations.freight.transeu.request.Spots;
import com.example.freight.v1.integrations.freight.transeu.request.TranseuFreightRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class RequestMapperTest {

    private static final String PATH = "src/test/resources/freight-request.json";

    private final RequestMapper requestMapper;

    public RequestMapperTest() {
        this.requestMapper = new RequestMapper();
    }


    @Test
    public void shouldReturnValidMainStructure() {

        //given
        FreightRequest freightRequest = parseJson();

        //when
        TranseuFreightRequest map = requestMapper.map(freightRequest);
        //then
//        assertEquals(1, map.getCapacity());
        assertEquals(Collections.EMPTY_LIST, map.getLoads());
        assertTrue(map.getPublish());

    }


    @Test
    public void shouldReturnValidRequirements() {
        //given
        FreightRequest freightRequest = parseJson();
        //when
        TranseuFreightRequest map = requestMapper.map(freightRequest);
        //then
        assertTrue(map.getRequirements().getIsFtl());
        assertEquals("curtainsider", map.getRequirements().getRequiredTruckBodies().getFirst().toLowerCase());
    }

    @Test
    public void shouldReturnValidSpots() {
        //given
        FreightRequest freightRequest = parseJson();
        //when
        TranseuFreightRequest map = requestMapper.map(freightRequest);
        //then
        assertEquals(2, map.getSpots().size());
        Spots loadingSpot = map.getSpots().getFirst();
        Spots unloadingSpot = map.getSpots().getLast();

        assertEquals(1, loadingSpot.getSpotOrder());
        assertEquals(1, loadingSpot.getOperations().getFirst().getOperationOrder());
        assertEquals("loading", loadingSpot.getOperations().getFirst().getType());
        assertEquals("2025-04-06T00:00:00+0100", loadingSpot.getOperations().getFirst().getTimespans().getBegin());
        assertEquals("2025-04-06T03:30:00+0100", loadingSpot.getOperations().getFirst().getTimespans().getEnd());

        assertEquals(2, unloadingSpot.getSpotOrder());
        assertEquals(1, unloadingSpot.getOperations().getFirst().getOperationOrder());
        assertEquals("unloading", unloadingSpot.getOperations().getFirst().getType());
        assertEquals("2025-04-06T00:00:00+0100", unloadingSpot.getOperations().getFirst().getTimespans().getBegin());
        assertEquals("2025-04-06T02:30:00+0100", unloadingSpot.getOperations().getFirst().getTimespans().getEnd());
    }



    private FreightRequest parseJson() {
        byte[] jsonData;
        try {
            jsonData = Files.readAllBytes(Paths.get(PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonData, FreightRequest.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}