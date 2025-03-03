package com.example.freight.utlis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

public class WebClientFilters {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebClientFilters.class);


    public static ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(request -> {
            LOGGER.info("Request: {} {}", request.method(), request.url());
            request.headers().forEach((name, values) ->
                    LOGGER.info("[Headers] {}: {}", name, String.join(", ", values))
            );
            return Mono.just(request);
        });
    }

    public static ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(response -> {
            LOGGER.info("Response: {}", response.statusCode());
            response.headers().asHttpHeaders().forEach((name, values) ->
                    LOGGER.info("{}: {}", name, String.join(", ", values))
            );
            return Mono.just(response);
        });
    }

}
