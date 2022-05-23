package com.example.rideshare.rideshare.services;

import com.example.rideshare.rideshare.repositories.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepo;


}
