package com.example.rideshare.rideshare.services;

import com.example.rideshare.rideshare.customexceptions.StationNotFoundException;
import com.example.rideshare.rideshare.dtos.StationDto;
import com.example.rideshare.rideshare.model.Station;
import com.example.rideshare.rideshare.repositories.StationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StationService {

    @Autowired
    private StationRepository stationRepo;

    @Autowired
    private ModelMapper mapper;

    public List<StationDto> getAllStations() {
        return stationRepo.findAll().stream()
                .map(station -> mapper.map(station, StationDto.class))
                .collect(Collectors.toList());
    }

    public StationDto getStationById(Long id) {
        Optional<Station> station = stationRepo.findById(id);

        if (station.isEmpty()) {
            throw new StationNotFoundException("Station not found for this id.");
        }

        return mapper.map(station.get(), StationDto.class);
    }

    public StationDto createStation(StationDto stationDto) {
        stationRepo.save(mapper.map(stationDto, Station.class));
        return stationDto;
    }

    public StationDto updateStation(Long id, StationDto stationDto) {
        Optional<Station> station = stationRepo.findById(id);

        if (station.isEmpty()) {
            throw new StationNotFoundException("Station not found for this id.");
        }
        stationDto.setId(id);

        if (stationDto.getCarsStationed() != null) {
            station.get().setCarsStationed(stationDto.getCarsStationed());
        }
        if (stationDto.getBikesStationed() != null) {
            station.get().setBikesStationed(stationDto.getBikesStationed());
        }
        if (stationDto.getCoordinatesX() != null) {
            station.get().setCoordinatesX(stationDto.getCoordinatesX());
        }
        if (stationDto.getCoordinatesY() != null) {
            station.get().setCoordinatesY(stationDto.getCoordinatesY());
        }
        if (stationDto.getName() != null) {
            station.get().setName(stationDto.getName());
        }
        if (stationDto.getOptimalBikeCount() != null) {
            station.get().setOptimalBikeCount(stationDto.getOptimalBikeCount());
        }
        if (stationDto.getTotalCars() != null) {
            station.get().setTotalCars(stationDto.getTotalCars());
        }
        if (stationDto.getTotalBikeCapacity() != null) {
            station.get().setTotalBikeCapacity(stationDto.getTotalBikeCapacity());
        }

        stationRepo.save(station.get());
        return mapper.map(station.get(), StationDto.class);
    }

    public void deleteStation(Long id) {
        Optional<Station> station = stationRepo.findById(id);

        if (station.isEmpty()) {
            throw new StationNotFoundException("Station not found for this id.");
        }

        stationRepo.delete(station.get());
    }
}
