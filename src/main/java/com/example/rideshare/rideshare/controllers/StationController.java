package com.example.rideshare.rideshare.controllers;

import com.example.rideshare.rideshare.dtos.StationDto;
import com.example.rideshare.rideshare.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stations")
public class StationController {

    @Autowired
    private StationService stationService;

    @GetMapping
    public ResponseEntity<List<StationDto>> getAllStations() {
        return ResponseEntity.ok().body(stationService.getAllStations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StationDto> getStationById(@PathVariable Long id) {
        return ResponseEntity.ok().body(stationService.getStationById(id));
    }

    @PostMapping
    public ResponseEntity<StationDto> createStation(@RequestBody StationDto stationDto) {
        return new ResponseEntity<>(stationService.createStation(stationDto), new HttpHeaders(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StationDto> updateStation(@PathVariable Long id, @RequestBody StationDto stationDto) {
        return new ResponseEntity<>(stationService.updateStation(id, stationDto), new HttpHeaders(), HttpStatus.RESET_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStation(@PathVariable Long id) {
        stationService.deleteStation(id);
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.RESET_CONTENT);
    }
}
