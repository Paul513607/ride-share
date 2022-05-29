package com.example.rideshare.rideshare.controllers;

import com.example.rideshare.rideshare.dtos.CarDto;
import com.example.rideshare.rideshare.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<List<CarDto>> getAllCars() {
        return ResponseEntity.ok().body(carService.getAllCars());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getCarById(@PathVariable Long id) {
        return ResponseEntity.ok().body(carService.getCarById(id));
    }

    @PostMapping
    public ResponseEntity<CarDto> createCar(@RequestBody CarDto carDto) {
        return new ResponseEntity<>(carService.createCar(carDto), new HttpHeaders(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDto> updateCar(@PathVariable Long id, @RequestBody CarDto carDto) {
        return new ResponseEntity<>(carService.updateCar(id, carDto), new HttpHeaders(), HttpStatus.RESET_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.RESET_CONTENT);
    }
}
