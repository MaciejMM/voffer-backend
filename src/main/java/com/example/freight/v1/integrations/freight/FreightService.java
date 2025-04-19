package com.example.freight.v1.integrations.freight;

import com.example.freight.auth.TokenServiceMapper;
import com.example.freight.utlis.JsonUtil;
import com.example.freight.v1.BaseService;
import com.example.freight.v1.integrations.FreightMapper;
import com.example.freight.v1.integrations.freight.dto.FreightDto;
import com.example.freight.v1.integrations.freight.entity.Freight;
import com.example.freight.v1.integrations.freight.transeu.TranseuFreightService;
import com.example.freight.v1.integrations.offer.HistoryService;
import com.example.freight.v1.integrations.offer.entity.OfferHistoryStatus;
import com.example.freight.v1.integrations.offer.transeu.response.TransEuResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FreightService extends BaseService {

    private final TokenServiceMapper tokenServiceMapper;
    private final TranseuFreightService transeuFreightService;
    private final FreightMapper freightMapper;
    private final HistoryService historyService;
    private final FreightRepository freightRepository;
    private final FreightDtoMapper freightDtoMapper;

    public FreightService(final TokenServiceMapper tokenServiceMapper, TranseuFreightService transeuFreightService, FreightMapper freightMapper, HistoryService historyService, FreightRepository freightRepository, FreightDtoMapper freightDtoMapper) {
        this.tokenServiceMapper = tokenServiceMapper;
        this.transeuFreightService = transeuFreightService;
        this.freightMapper = freightMapper;
        this.historyService = historyService;
        this.freightRepository = freightRepository;
        this.freightDtoMapper = freightDtoMapper;
    }

    @Transactional
    public FreightDto createFreight(final FreightRequest freightRequest, final Map<String, String> headers) {
        LOGGER.info("Creating freight: {}", JsonUtil.toJson(freightRequest));
        final Map<String, String> tokenMap = tokenServiceMapper.map(headers);
        final TransEuResponse transEuResponse = transeuFreightService.createFreight(freightRequest, tokenMap);
        final Freight map = freightMapper.map(transEuResponse, freightRequest);

        historyService.save(map, OfferHistoryStatus.CREATED);
        final Freight save = freightRepository.save(map);
        return freightDtoMapper.map(save);
    }

    @Transactional
    public List<FreightDto> getFreights() {
        final List<Freight> freightList = freightRepository.findAll();
        return freightList.stream()
                .map(freightDtoMapper::map)
                .toList();
    }
}
