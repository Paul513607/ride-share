package com.example.rideshare.rideshare.services;

import com.example.rideshare.rideshare.customexceptions.CarNotFoundException;
import com.example.rideshare.rideshare.dtos.CarDto;
import com.example.rideshare.rideshare.model.Car;
import com.example.rideshare.rideshare.repositories.CarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepo;

    @Autowired
    private ModelMapper mapper;

    public List<CarDto> getAllCars() {
        return carRepo.findAll().stream()
                .map(car -> mapper.map(car, CarDto.class))
                .collect(Collectors.toList());
    }

    public CarDto getCarById(Long id) {
        Optional<Car> car = carRepo.findById(id);

        if (car.isEmpty()) {
            throw new CarNotFoundException("Car not found for this id.");
        }

        return mapper.map(car.get(), CarDto.class);
    }

    public CarDto createCar(CarDto carDto) {
        carRepo.save(mapper.map(carDto, Car.class));
        return carDto;
    }

    public CarDto updateCar(Long id, CarDto carDto) {
        Optional<Car> car = carRepo.findById(id);

        if (car.isEmpty()) {
            throw new CarNotFoundException("Car not found for this id.");
        }
        carDto.setId(id);

        if (carDto.getCurrentLoad() != null) {
            car.get().setCurrentLoad(carDto.getCurrentLoad());
        }
        if (carDto.getTotalCapacity() != null) {
            car.get().setTotalCapacity(carDto.getTotalCapacity());
        }
        if (carDto.getCurrentRoute() != null) {
            car.get().setCurrentRoute(carDto.getCurrentRoute());
        }

        carRepo.save(car.get());
        return mapper.map(car.get(), CarDto.class);
    }

    public void deleteCar(Long id) {
        Optional<Car> car = carRepo.findById(id);

        if (car.isEmpty()) {
            throw new CarNotFoundException("Car not found for this id.");
        }

        carRepo.delete(car.get());
    }
}
