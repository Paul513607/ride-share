package com.example.rideshare.rideshare.customexceptions;

public class BikeNotFoundException extends RuntimeException {
    public BikeNotFoundException(String message) {
        super(message);
    }
}
