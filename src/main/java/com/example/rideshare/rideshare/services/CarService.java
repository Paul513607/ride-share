package com.example.rideshare.rideshare.services;

import com.example.rideshare.rideshare.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepo;


}
