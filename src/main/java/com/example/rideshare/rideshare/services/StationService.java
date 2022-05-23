package com.example.rideshare.rideshare.services;

import com.example.rideshare.rideshare.repositories.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StationService {

    @Autowired
    private StationRepository stationRepo;


}
