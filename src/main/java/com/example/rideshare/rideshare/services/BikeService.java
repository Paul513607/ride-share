package com.example.rideshare.rideshare.services;

import com.example.rideshare.rideshare.repositories.BikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BikeService {

    @Autowired
    private BikeRepository bikeRepo;

    public int testP() {
        return bikeRepo.testP();
    }

}
