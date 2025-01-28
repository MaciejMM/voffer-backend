package com.example.freight.v1.vehicleOffer.service.teleroute;

import com.example.freight.v1.vehicleOffer.model.offer.LoadingType;
import com.example.freight.v1.vehicleOffer.model.offer.VehicleOfferRequest;
import com.example.freight.v1.vehicleOffer.model.teleroute.request.TelerouteRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class TelerouteRequestMapperTest {

    @InjectMocks
    private TelerouteRequestMapper telerouteRequestMapper;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(telerouteRequestMapper, "ownerLogin", "teleroute_user");
    }

    @Test
    void shouldMapVehicleOfferRequest() {
        //given
        VehicleOfferRequest vehicleOfferRequest = VehicleOfferRequest.builder()
                .loadingPlace(
                        new VehicleOfferRequest.LoadingPlace("PL", "89-000", "Wroclaw",
                                LocalDateTime.of(2025, 5, 11, 13, 0),
                                LocalDateTime.of(2025, 5, 11, 15, 0)))
                .unloadingPlace(
                        VehicleOfferRequest.UnloadingPlace.builder()
                                .country("PL")
                                .city("Szczecin")
                                .postalCode("54-000")
                                .unloadingEndDateAndTime(LocalDateTime.of(2025, 5, 13, 14, 0))
                                .unloadingStartDateAndTime(LocalDateTime.of(2025, 5, 13, 14, 0))
                                .city("TestCity")
                                .build())
                .description("test description")
                .loadingType(LoadingType.FTL)
                .loadingWeight("24.0")
                .loadingLength("12.0")
                .loadingVolume(null)
                .loadingBodyType("Tautliner")
                .publishSelected(null)
                .build();

        //when
        TelerouteRequest map = telerouteRequestMapper.map(vehicleOfferRequest);

        //then
        assertEquals("PL", map.getDeparture().getLocation().getAddress().getCountry());
        assertEquals("89-000", map.getDeparture().getLocation().getAddress().getZip());
        assertEquals("Wroclaw", map.getDeparture().getLocation().getAddress().getCity());
        assertEquals("54-000", map.getArrival().getLocation().getAddress().getZip());
        assertEquals("TestCity", map.getArrival().getLocation().getAddress().getCity());
        assertEquals("PL", map.getArrival().getLocation().getAddress().getCountry());
        assertEquals("teleroute_user", map.getOwner().getLogin());
        assertEquals("2025-05-11T13:00:00", map.getDeparture().getInterval().getStart());
        assertEquals("2025-05-11T15:00:00", map.getDeparture().getInterval().getEnd());
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
                        .loadingStartDateAndTime(LocalDateTime.of(2025, 5, 11, 13, 0))
                        .loadingEndDateAndTime(LocalDateTime.of(2025, 5, 11, 15, 0))
                        .build())
                .unloadingPlace(VehicleOfferRequest.UnloadingPlace.builder()
                        .country("PL")
                        .city("Szczecin")
                        .postalCode("54-000")
                        .unloadingStartDateAndTime(LocalDateTime.of(2025, 5, 13, 14, 0))
                        .unloadingEndDateAndTime(LocalDateTime.of(2025, 5, 13, 14, 0))
                        .build())
                .loadingWeight(null)
                .loadingLength(null)
                .loadingVolume(null)
                .description(null)
                .loadingBodyType("Tautliner")
                .build();
        //when
        TelerouteRequest map = telerouteRequestMapper.map(vehicleOfferRequest);
        //then
        assertNull(map.getLoadDescription().getWeight());
        assertNull(map.getLoadDescription().getLength());
        assertNull(map.getLoadDescription().getVolume());

    }

}