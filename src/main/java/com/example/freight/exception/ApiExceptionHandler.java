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
        HttpStatus badRequestStatus = HttpStatus.BAD_REQUEST;
        ApiException apiException = createApiException(apiRequestException, badRequestStatus);
        return new ResponseEntity<>(apiException, badRequestStatus);
    }

    @ExceptionHandler(value = {AuthorizationException.class})
    public ResponseEntity<Object> handleAuthorizationException(final AuthorizationException apiRequestException) {
        HttpStatus unauthorizedStatus = HttpStatus.UNAUTHORIZED;
        ApiException apiException = createApiException(apiRequestException, unauthorizedStatus);
        return new ResponseEntity<>(apiException, unauthorizedStatus);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFoundException(final UserNotFoundException userNotFoundException) {
        HttpStatus notFoundStatus = HttpStatus.NOT_FOUND;
        ApiException apiException = createApiException(userNotFoundException, notFoundStatus);
        return new ResponseEntity<>(apiException, notFoundStatus);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(final NotFoundException notFoundException) {
        HttpStatus notFoundStatus = HttpStatus.NOT_FOUND;
        ApiException apiException = createApiException(notFoundException, notFoundStatus);
        return new ResponseEntity<>(apiException, notFoundStatus);
    }

    @ExceptionHandler(value = {ServerResponseException.class})
    public ResponseEntity<Object> handleServerResponseException(final ServerResponseException serverResponseException) {
        HttpStatus notFoundStatus = HttpStatus.BAD_GATEWAY;

        ApiException apiException = createApiException(serverResponseException, notFoundStatus);
        return new ResponseEntity<>(apiException, notFoundStatus);
    }

    @ExceptionHandler(value = {RoleNotAllowedException.class})
    public ResponseEntity<Object> handleRoleNotAllowedException(final RoleNotAllowedException roleNotAllowedException) {
        HttpStatus forbiddenStatus = HttpStatus.FORBIDDEN;
        ApiException apiException = createApiException(roleNotAllowedException, forbiddenStatus);
        return new ResponseEntity<>(apiException, forbiddenStatus);
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
