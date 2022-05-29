package com.example.rideshare.rideshare.controllers;

import com.example.rideshare.rideshare.dtos.RouteDto;
import com.example.rideshare.rideshare.services.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/routes")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping
    public ResponseEntity<List<RouteDto>> getAllRoutes() {
        return ResponseEntity.ok().body(routeService.getAllRoutes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteDto> getRouteById(@PathVariable Long id) {
        return ResponseEntity.ok().body(routeService.getRouteById(id));
    }

    @PostMapping
    public ResponseEntity<RouteDto> createRoute(@RequestBody RouteDto routeDto) {
        return new ResponseEntity<>(routeService.createRoute(routeDto), new HttpHeaders(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RouteDto> updateRoute(@PathVariable Long id, @RequestBody RouteDto routeDto) {
        return new ResponseEntity<>(routeService.updateRoute(id, routeDto), new HttpHeaders(), HttpStatus.RESET_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.RESET_CONTENT);
    }
}
