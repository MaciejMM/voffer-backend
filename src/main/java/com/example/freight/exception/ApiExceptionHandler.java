package com.example.freight.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;


@ControllerAdvice
public class ApiExceptionHandler {

    private static final String ZONE_ID = "Z";
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(final ApiRequestException apiRequestException) {
        HttpStatus badRequestStatus = HttpStatus.NOT_FOUND;
        ApiException apiException = createApiException(apiRequestException, badRequestStatus);
        return new ResponseEntity<>(apiException, badRequestStatus);
    }

    @ExceptionHandler(value = {AuthorizationException.class})
    public ResponseEntity<Object> handleAuthorizationException(final AuthorizationException apiRequestException) {
        HttpStatus badRequestStatus = HttpStatus.NOT_ACCEPTABLE;
        ApiException apiException = createApiException(apiRequestException, badRequestStatus);
        return new ResponseEntity<>(apiException, badRequestStatus);
    }


    private ApiException createApiException(final Exception exception, final HttpStatus status) {
        LOGGER.error(exception.getMessage(), exception);
        return new ApiException(
                exception.getMessage(),
                status,
                ZonedDateTime.now(ZoneId.of(ZONE_ID))
        );
    }
}
