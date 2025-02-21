package com.example.freight.v1.vehicleOffer.service.teleroute;

import com.example.freight.v1.vehicleOffer.model.offer.LoadingType;
import com.example.freight.v1.vehicleOffer.model.offer.VehicleOfferRequest;
import com.example.freight.v1.vehicleOffer.model.teleroute.request.TelerouteRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ContextConfiguration(classes = {TelerouteRequestMapper.class})
public class TelerouteRequestMapperTest {

    private final TelerouteRequestMapper telerouteRequestMapper;

    public TelerouteRequestMapperTest() {
        this.telerouteRequestMapper = new TelerouteRequestMapper();
    }

    @Test
    void shouldMapVehicleOfferRequest() {
        //given
        VehicleOfferRequest vehicleOfferRequest = VehicleOfferRequest.builder()
                .loadingPlace(
                        VehicleOfferRequest.LoadingPlace.builder()
                                .postalCode("89-000")
                                .city("Wroclaw")
                                .loadingStartDateAndTime("2025-01-28T10:00:00.000Z")
                                .loadingEndDateAndTime("2025-01-28T12:30:00.000Z")
                                .country("PL")
                                .build()
                )
                .unloadingPlace(
                        List.of(
                                VehicleOfferRequest.UnloadingPlace.builder()
                                        .country("PL")
                                        .city("Szczecin")
                                        .postalCode("54-000")
                                        .unloadingStartDateAndTime("2025-01-31T12:00:00.000Z")
                                        .unloadingEndDateAndTime("2025-01-31T14:00:00.000Z")
                                        .city("TestCity")
                                        .build())
                )

                .description("test description")
                .loadingType(LoadingType.FTL)
                .loadingWeight("24.0")
                .loadingLength("12.0")
                .loadingVolume(null)
                .loadingBodyType("Tautliner")
                .publishSelected(null)
                .build();

        String telerouteUser = "teleroute_user";
        //when
        TelerouteRequest map = telerouteRequestMapper.map(vehicleOfferRequest, telerouteUser);

        //then
        assertEquals("PL", map.getDeparture().getLocation().getAddress().getCountry());
        assertEquals("89-000", map.getDeparture().getLocation().getAddress().getZip());
        assertEquals("Wroclaw", map.getDeparture().getLocation().getAddress().getCity());
        assertEquals("2025-01-28T10:00:00", map.getDeparture().getInterval().getStart());
        assertEquals("2025-01-28T12:30:00", map.getDeparture().getInterval().getEnd());

        assertEquals("54-000", map.getArrival().getLocation().getAddress().getZip());
        assertEquals("TestCity", map.getArrival().getLocation().getAddress().getCity());
        assertEquals("2025-01-31T12:00:00", map.getArrival().getInterval().getStart());
        assertEquals("2025-01-31T14:00:00", map.getArrival().getInterval().getEnd());
        assertEquals("PL", map.getArrival().getLocation().getAddress().getCountry());
        assertEquals("teleroute_user", map.getOwner().getLogin());

        assertEquals("Tautliner", map.getLoadDescription().getVehicle());
        assertEquals(12.0, map.getLoadDescription().getLength());
        assertNull(map.getLoadDescription().getVolume());
        assertEquals(24.0, map.getLoadDescription().getWeight());
    }

    @Test
    public void shouldHandleNullsInDescriptionMapper() {
        //given
        VehicleOfferRequest vehicleOfferRequest = VehicleOfferRequest.builder()
                .loadingPlace(VehicleOfferRequest.LoadingPlace.builder()
                        .country("PL")
                        .city("Wroclaw")
                        .postalCode("89-000")
                        .loadingStartDateAndTime("2025-01-28T10:00:00.000Z")
                        .loadingEndDateAndTime("2025-01-28T12:30:00.000Z")
                        .build())
                .unloadingPlace(
                        List.of(VehicleOfferRequest.UnloadingPlace.builder()
                                .country("PL")
                                .city("Szczecin")
                                .postalCode("54-000")
                                .unloadingEndDateAndTime("2025-01-31T12:00:00.000Z")
                                .unloadingStartDateAndTime("2025-01-31T14:30:00.000Z")
                                .build())
                )
                .loadingWeight(null)
                .loadingLength(null)
                .loadingVolume(null)
                .description(null)
                .loadingBodyType("Tautliner")
                .build();
        String telerouteUser = "teleroute_user";
        //when
        TelerouteRequest map = telerouteRequestMapper.map(vehicleOfferRequest, telerouteUser);
        //then
        assertNull(map.getLoadDescription().getWeight());
        assertNull(map.getLoadDescription().getLength());
        assertNull(map.getLoadDescription().getVolume());

    }

}