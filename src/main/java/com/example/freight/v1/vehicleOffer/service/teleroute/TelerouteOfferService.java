package com.example.freight.v1.vehicleOffer.service.teleroute;

import com.example.freight.utlis.JsonUtil;
import com.example.freight.v1.vehicleOffer.model.offer.VehicleOfferRequest;
import com.example.freight.v1.vehicleOffer.model.teleroute.request.TelerouteRequest;
import com.example.freight.v1.vehicleOffer.model.teleroute.response.TelerouteResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Map;

@Service
public class TelerouteOfferService {

    private final TelerouteRequestMapper telerouteRequestMapper;
    private final TelerouteService telerouteService;
    private static final Logger LOGGER = LoggerFactory.getLogger(TelerouteOfferService.class);

    public TelerouteOfferService(final TelerouteRequestMapper telerouteRequestMapper, final TelerouteService telerouteService) {
        this.telerouteRequestMapper = telerouteRequestMapper;
        this.telerouteService = telerouteService;
    }

    public TelerouteResponseDto createOffer(final VehicleOfferRequest vehicleOfferRequest, Map<String, String> tokenMap) {
        final TelerouteToken telerouteToken = decodeAccessToken(tokenMap.get("teleroute_access_token"));
        final TelerouteRequest map = telerouteRequestMapper.map(vehicleOfferRequest, telerouteToken.getUserName());
        try {
            return telerouteService.createOffer(map, tokenMap.get("teleroute_access_token"));
        } catch (Exception e) {
            LOGGER.error("Error creating offer", e);
            return TelerouteResponseDto.builder().errorMessage(e.getMessage()).offerId(null).externalId(null).publishDateTime(null).build();
        }
    }


    public TelerouteResponseDto refreshOffer(final VehicleOfferRequest vehicleOfferRequest, final String offerId, Map<String, String> tokenMap) {
        final TelerouteToken telerouteToken = decodeAccessToken(tokenMap.get("teleroute_access_token"));
        final TelerouteRequest map = telerouteRequestMapper.map(vehicleOfferRequest, telerouteToken.getUserName());
        try {
            return telerouteService.updateOffer(map, offerId, tokenMap.get("teleroute_access_token"));
        } catch (Exception e) {
            LOGGER.error("Error creating offer", e);
            return TelerouteResponseDto.builder().errorMessage(e.getMessage()).offerId(null).externalId(null).publishDateTime(null).build();
        }
    }

    private TelerouteToken decodeAccessToken(String accessToken) {
        try {
            final String[] chunks = accessToken.split("\\.");
            final Base64.Decoder decoder = Base64.getUrlDecoder();
            final String payload = new String(decoder.decode(chunks[1]));
            return JsonUtil.fromJson(payload, TelerouteToken.class);
        } catch (Exception e) {
            LOGGER.error("Invalid Teleroute access token {}", accessToken, e);
        }
        return TelerouteToken
                .builder()
                .user_name(null)
                .exp(null)
                .build();
    }

    @AllArgsConstructor
    @Builder
    private static class TelerouteToken {
        private String user_name;
        private Long exp;

        public String getUserName() {
            return user_name;
        }
    }

}
