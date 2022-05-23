package com.example.rideshare.rideshare.controllers;

import com.example.rideshare.rideshare.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stations")
public class StationController {

    @Autowired
    private StationService stationService;

}
