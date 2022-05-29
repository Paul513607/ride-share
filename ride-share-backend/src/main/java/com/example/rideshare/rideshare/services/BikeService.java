package com.example.rideshare.rideshare.services;

import com.example.rideshare.rideshare.customexceptions.BikeNotFoundException;
import com.example.rideshare.rideshare.dtos.BikeDto;
import com.example.rideshare.rideshare.model.Bike;
import com.example.rideshare.rideshare.repositories.BikeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BikeService {

    @Autowired
    private BikeRepository bikeRepo;

    @Autowired
    private ModelMapper mapper;

    public List<BikeDto> getAllBikes() {
        return bikeRepo.findAll().stream()
                .map(bike -> mapper.map(bike, BikeDto.class))
                .collect(Collectors.toList());
    }

    public BikeDto getBikeById(Long id) {
        Optional<Bike> bike = bikeRepo.findById(id);

        if (bike.isEmpty()) {
            throw new BikeNotFoundException("Bike not found for this id.");
        }

        return mapper.map(bike.get(), BikeDto.class);
    }

    public BikeDto createBike(BikeDto bikeDto) {
        bikeRepo.save(mapper.map(bikeDto, Bike.class));
        return bikeDto;
    }

    public BikeDto updateBike(Long id, BikeDto bikeDto) {
        Optional<Bike> bike = bikeRepo.findById(id);

        if (bike.isEmpty()) {
            throw new BikeNotFoundException("Bike not found for this id.");
        }
        bikeDto.setId(id);

        if (bikeDto.getInMaintenance() != null) {
            bike.get().setInMaintenance(bikeDto.getInMaintenance());
        }
        if (bikeDto.getTimeUsedSeconds() != null) {
            bike.get().setTimeUsedSeconds(bikeDto.getTimeUsedSeconds());
        }
        if (bikeDto.getRoute() != null) {
            bike.get().setRoute(bikeDto.getRoute());
        }
        if (bikeDto.getStation() != null) {
            bike.get().setStation(bikeDto.getStation());
        }

        bikeRepo.save(bike.get());
        return mapper.map(bike.get(), BikeDto.class);
    }

    public void deleteBike(Long id) {
        Optional<Bike> bike = bikeRepo.findById(id);

        if (bike.isEmpty()) {
            throw new BikeNotFoundException("Bike not found for this id.");
        }

        bikeRepo.delete(bike.get());
    }
}
