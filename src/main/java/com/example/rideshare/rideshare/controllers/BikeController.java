package com.example.rideshare.rideshare.controllers;

import com.example.rideshare.rideshare.services.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bikes")
public class BikeController {

    @Autowired
    private BikeService bikeService;

}
