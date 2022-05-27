package com.example.rideshare.rideshare.algo.datastructures.bikesharing;

import com.example.rideshare.rideshare.algo.datastructures.graph.Graph;
import com.example.rideshare.rideshare.algo.datastructures.model.Station;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GreedySolutionGenerator implements SolutionGenerator{

    private Graph<Station> graph;
    private int truckCapacity;
    public GreedySolutionGenerator(Graph<Station> graph, int truckCapacity){
        this.graph = new Graph<>(graph);
        this.truckCapacity = truckCapacity;
    }

    @Override
    public SBRPSolution getSolution() {
        int currentCapacity = truckCapacity;
        SBRPSolution solution = new SBRPSolution();
        solution.addNewStation(0, 0, 0);

        List<Integer> OVTemp = graph.getVerticesMap().entrySet().stream()
                .filter(entry -> entry.getValue().getDemand() != 0 || ThreadLocalRandom.current().nextBoolean())
                .map(Map.Entry::getKey).toList();

        Collections.shuffle(OVTemp);
        Set<Integer> OV = new HashSet<>(OVTemp);


        while (OV.size() > 0){
            boolean inserted = false;
            for (Integer station:
                 OV) {
                int stationDemand = graph.getVerticesMap().get(station).getDemand();
                if((stationDemand <= 0 && (-stationDemand) >= currentCapacity) ||
                        (stationDemand > 0 && truckCapacity - currentCapacity >= stationDemand)){
                    inserted = true;
                    solution.addNewStation(station, stationDemand, truckCapacity - currentCapacity);
                    currentCapacity += stationDemand;
                    OV.remove(station);
                    break;
                }
            }
            if(!inserted){
                Integer lastStationInSolution = solution.getRoutes().get(solution.getRoutes().size() - 1);
                Integer maxExchangeStation = OV.stream().findFirst().get();
                double distanceFromPrevious = Double.MAX_VALUE;
                int maxExchange = 0;
                for (Integer station:
                     OV) {
                    int stationDemand = graph.getVerticesMap().get(station).getDemand();
                    int stationExchange;
                    if(stationDemand >= 0){
                        stationExchange = truckCapacity - currentCapacity;
                    }
                    else {
                        stationExchange = currentCapacity;
                    }

                    if(stationExchange > maxExchange){
                        maxExchangeStation = station;
                        maxExchange = stationExchange;
                        distanceFromPrevious = Station.getDistance(graph.getVerticesMap().get(lastStationInSolution), graph.getVerticesMap().get(station));
                    }
                    else if(stationExchange == maxExchange){
                        double currentDistanceFromPrevious = Station.getDistance(graph.getVerticesMap().get(lastStationInSolution), graph.getVerticesMap().get(station));
                        if(distanceFromPrevious > currentDistanceFromPrevious){
                            maxExchangeStation = station;
                        }
                    }
                }

                int stationDemand = graph.getVerticesMap().get(maxExchangeStation).getDemand();
                int vehicleAction =  stationDemand < 0 ? -currentCapacity : truckCapacity - currentCapacity;
                solution.addNewStation(maxExchangeStation, vehicleAction, truckCapacity - currentCapacity);
                currentCapacity += vehicleAction;
                Station referredStation = graph.getVerticesMap().get(maxExchangeStation);
                referredStation.setStationed(referredStation.getStationed() + vehicleAction);
            }
        }

        return solution;
    }
}
