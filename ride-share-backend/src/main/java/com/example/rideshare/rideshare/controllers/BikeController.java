package com.example.rideshare.rideshare.controllers;

import com.example.rideshare.rideshare.dtos.BikeDto;
import com.example.rideshare.rideshare.services.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bikes")
public class BikeController {

    @Autowired
    private BikeService bikeService;

    @GetMapping
    public ResponseEntity<List<BikeDto>> getAllBikes() {
        return ResponseEntity.ok().body(bikeService.getAllBikes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BikeDto> getBikeById(@PathVariable Long id) {
        return ResponseEntity.ok().body(bikeService.getBikeById(id));
    }

    @PostMapping
    public ResponseEntity<BikeDto> createBike(@RequestBody BikeDto bikeDto) {
        return new ResponseEntity<>(bikeService.createBike(bikeDto), new HttpHeaders(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BikeDto> updateBike(@PathVariable Long id, @RequestBody BikeDto bikeDto) {
        return new ResponseEntity<>(bikeService.updateBike(id, bikeDto), new HttpHeaders(), HttpStatus.RESET_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBike(@PathVariable Long id) {
        bikeService.deleteBike(id);
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.RESET_CONTENT);
    }
}
