package com.example.rideshare.rideshare.services;

import com.example.rideshare.rideshare.customexceptions.RouteNotFoundException;
import com.example.rideshare.rideshare.dtos.RouteDto;
import com.example.rideshare.rideshare.model.Route;
import com.example.rideshare.rideshare.repositories.RouteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepo;

    @Autowired
    private ModelMapper mapper;

    public List<RouteDto> getAllRoutes() {
        return routeRepo.findAll().stream()
                .map(route -> mapper.map(route, RouteDto.class))
                .collect(Collectors.toList());
    }

    public RouteDto getRouteById(Long id) {
        Optional<Route> route = routeRepo.findById(id);

        if (route.isEmpty()) {
            throw new RouteNotFoundException("Route not found for this id.");
        }

        return mapper.map(route.get(), RouteDto.class);
    }

    public RouteDto createRoute(RouteDto routeDto) {
        routeRepo.save(mapper.map(routeDto, Route.class));
        return routeDto;
    }

    public RouteDto updateRoute(Long id, RouteDto routeDto) {
        Optional<Route> route = routeRepo.findById(id);

        if (route.isEmpty()) {
            throw new RouteNotFoundException("Route not found for this id.");
        }
        routeDto.setId(id);

        if (routeDto.getLength() != null) {
            route.get().setLength(routeDto.getLength());
        }
        if (routeDto.getStationSrc() != null) {
            route.get().setStationDest(routeDto.getStationSrc());
        }
        if (routeDto.getStationDest() != null) {
            route.get().setStationDest(routeDto.getStationDest());
        }

        routeRepo.save(route.get());
        return mapper.map(route.get(), RouteDto.class);
    }

    public void deleteRoute(Long id) {
        Optional<Route> route = routeRepo.findById(id);

        if (route.isEmpty()) {
            throw new RouteNotFoundException("Route not found for this id.");
        }

        routeRepo.delete(route.get());
    }
}
