package com.example.rideshare.rideshare.advice;

import com.example.rideshare.rideshare.customexceptions.*;
import com.example.rideshare.rideshare.util.JsonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BikeNotFoundException.class)
    public ResponseEntity<Object> bikeNotFound(BikeNotFoundException bikeException) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("Timestamp", LocalDateTime.now());
        result.put("Message", bikeException.getMessage());
        return new ResponseEntity<>(JsonUtil.objectToJsonString(result), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<Object> carNotFound(CarNotFoundException carException) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("Timestamp", LocalDateTime.now());
        result.put("Message", carException.getMessage());
        return new ResponseEntity<>(JsonUtil.objectToJsonString(result), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RouteNotFoundException.class)
    public ResponseEntity<Object> routeNotFound(RouteNotFoundException routeException) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("Timestamp", LocalDateTime.now());
        result.put("Message", routeException.getMessage());
        return new ResponseEntity<>(JsonUtil.objectToJsonString(result), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StationNotFoundException.class)
    public ResponseEntity<Object> stationNotFound(StationNotFoundException stationException) {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("Timestamp", LocalDateTime.now());
        result.put("Message", stationException.getMessage());
        return new ResponseEntity<>(JsonUtil.objectToJsonString(result), HttpStatus.NOT_FOUND);
    }
}