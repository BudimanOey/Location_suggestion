package com.bwslc.locationsuggestion.handler;

import java.security.InvalidParameterException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.bwslc.locationsuggestion.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ NoResourceFoundException.class })
    public ResponseEntity<ErrorResponse> handleNoResourceException(NoResourceFoundException exception) {
        ZonedDateTime timestamp = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        String formattedTimestamp = timestamp.format(formatter);
        ErrorResponse errorDTO = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "No Resource Found", exception.getMessage(),
                formattedTimestamp);
        ResponseEntity<ErrorResponse> errorResponse = new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
        return errorResponse;
    }

    @ExceptionHandler({ InvalidParameterException.class })
    public ResponseEntity<ErrorResponse> handleInvalidParameterException(InvalidParameterException exception) {
        ZonedDateTime timestamp = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        String formattedTimestamp = timestamp.format(formatter);
        ErrorResponse errorDTO = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Invalid Parameter", exception.getMessage(),
                formattedTimestamp);
        ResponseEntity<ErrorResponse> errorResponse = new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
        return errorResponse;
    }

}
