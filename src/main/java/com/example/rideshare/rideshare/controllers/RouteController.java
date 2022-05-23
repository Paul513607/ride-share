package com.example.rideshare.rideshare.controllers;

import com.example.rideshare.rideshare.services.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/routes")
public class RouteController {

    @Autowired
    private RouteService routeService;


}
