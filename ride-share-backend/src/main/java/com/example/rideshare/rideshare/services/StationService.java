package com.example.rideshare.rideshare.services;

import com.example.rideshare.rideshare.algo.graph.Graph;
import com.example.rideshare.rideshare.algo.models.AlgStation;
import com.example.rideshare.rideshare.algo.models.Solution;
import com.example.rideshare.rideshare.algo.solver.GreedySBRPSolver;
import com.example.rideshare.rideshare.customexceptions.StationNotFoundException;
import com.example.rideshare.rideshare.dtos.StationDto;
import com.example.rideshare.rideshare.model.Station;
import com.example.rideshare.rideshare.repositories.StationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StationService {

    @Autowired
    private StationRepository stationRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CarService carService;

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

    public void initProblem(){
        stationRepo.initProblem();
    }

    public Solution getTruckRoute(){
        var allStations = getAllStations();
        Map<Integer,Long> dataBaseAlgIdMapping = new HashMap<>();
        Graph<AlgStation> graph = new Graph<>(allStations.size());

        for (int index = 0; index < allStations.size(); index++) {
            dataBaseAlgIdMapping.put(index, allStations.get(index).getId());
            var stationDto = allStations.get(index);
            AlgStation station = new AlgStation(
                    stationDto.getName(),
                    stationDto.getCoordinatesX().doubleValue(),
                    stationDto.getCoordinatesY().doubleValue(),
                    (int)stationDto.getTotalBikeCapacity().longValue(),
                    (int)stationDto.getBikesStationed().longValue(),
                    (int)stationDto.getOptimalBikeCount().longValue());
            graph.getVerticesMap().put(index, station);
        }

        for (int start = 0; start < allStations.size() - 1; start++) {
            for (int end = start + 1; end < allStations.size(); end++) {
                graph.setUndirectedEdge(start, end, AlgStation.getDistance(graph.getVerticesMap().get(start), graph.getVerticesMap().get(end)));
            }
        }

        var car = carService.getAllCars().get(0);

        GreedySBRPSolver solver = new GreedySBRPSolver(graph, (int)car.getTotalCapacity().longValue());
        Solution solution = solver.getTruckRoute();
        var routes = solution.getRoutes();
        for (int index = 0; index < routes.size(); index++) {
            routes.set(index, (int)dataBaseAlgIdMapping.get(routes.get(index)).longValue());
        }

        return solution;
    }
}
