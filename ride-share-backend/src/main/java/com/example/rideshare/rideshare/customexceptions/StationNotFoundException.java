package com.example.rideshare.rideshare.customexceptions;

public class StationNotFoundException extends RuntimeException {
    public StationNotFoundException(String message) {
        super(message);
    }
}

