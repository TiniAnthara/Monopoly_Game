package com.game.monopoly.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = { Exception.class }) // Handles all exceptions by default (can be customized)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        // You can implement more specific handling for different exception types here
        String errorMessage = ex.getMessage() != null ? ex.getMessage() : "Internal server error";
        ErrorResponse errorResponse = new ErrorResponse(errorMessage);
        return new ResponseEntity<>(errorResponse, getHttpStatus(ex));
    }

    private HttpStatus getHttpStatus(Exception ex) {
        // Map specific exceptions to corresponding HTTP status codes
        if (ex instanceof IllegalArgumentException) {
            return HttpStatus.BAD_REQUEST;
        } else if (ex instanceof ResourceNotFoundException) {
            return HttpStatus.NOT_FOUND;
        } else if (ex instanceof IllegalStateException) {
            return HttpStatus.BAD_REQUEST;
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
