package com.example.rideshare.rideshare.algo.solver;

import com.example.rideshare.rideshare.algo.graph.Graph;
import com.example.rideshare.rideshare.algo.models.AlgStation;
import com.example.rideshare.rideshare.algo.models.Solution;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GreedySBRPSolver {
    private Graph<AlgStation> graph;
    private Integer truckCapacity;

    public GreedySBRPSolver(Graph<AlgStation> graph, int truckCapacity){
        this.graph = graph;
        this.truckCapacity = truckCapacity;
    }

    public Graph<AlgStation> getGraph() {
        return graph;
    }

    public void setGraph(Graph<AlgStation> graph) {
        if(graph == null) throw new IllegalArgumentException();
        this.graph = graph;
    }

    public Integer getTruckCapacity() {
        return truckCapacity;
    }

    public void setTruckCapacity(Integer truckCapacity) {
        if(truckCapacity == null || truckCapacity < 1) throw new IllegalArgumentException();
        this.truckCapacity = truckCapacity;
    }

    public Solution getTruckRoute() {
        int currentCapacity = truckCapacity;
        AlgStation depot = graph.getVerticesMap().get(0);
        long totalOptimalStationed = graph.getVerticesMap().entrySet().stream().skip(1)
                .map(entry -> entry.getValue().getOptimalStationed()).reduce(0, Integer::sum);
        long totalStationed = graph.getVerticesMap().entrySet().stream().skip(1)
                .map(entry -> entry.getValue().getStationed()).reduce(0, Integer::sum);

        //if by summing the demands we get extra or not enough bikes to fulfill the requests there is no solution
//        if((totalDemand > 0 && totalDemand > Math.min(depot.getBikesStationed(), truckCapacity)) ||
//                (totalDemand < 0 && Math.abs(totalDemand) > Math.min(depot.getCapacity() - depot.getStationed(), truckCapacity))){
//            return null;
//        }

        if(totalOptimalStationed != totalStationed){
            return null;
        }

        Solution solution = new Solution();
        solution.addNewStation(0, 0, 0);


        List<Integer> toVisitList = graph.getVerticesMap().entrySet().stream().skip(1)
                .filter(entry -> entry.getValue().getDemand() != 0 || ThreadLocalRandom.current().nextBoolean())
                .map(Map.Entry::getKey).toList();

        //Collections.shuffle(toVisitList);
        Set<Integer> toVisitSet = new HashSet<>(toVisitList);

        var stations = graph.getVerticesMap();
        while (toVisitSet.size() > 0){
            boolean inserted = false;
            var lastVisitedStation = stations.get(solution.getRoutes().get(solution.getRoutes().size() - 1));
            var sortedStations = sortByDistanceTo(lastVisitedStation, toVisitSet).stream().map(Tuple::getFirst).toList();
            for (Integer station:
                    sortedStations) {
                int stationDemand = stations.get(station).getDemand();
                if((stationDemand <= 0 && (-stationDemand) <= currentCapacity) ||
                        (stationDemand > 0 && truckCapacity - currentCapacity >= stationDemand)){
                    inserted = true;
                    solution.addNewStation(station, stationDemand, truckCapacity - currentCapacity);
                    currentCapacity += stationDemand;
                    toVisitSet.remove(station);
                    break;
                }
            }
            if(!inserted){
                Integer lastStationInSolution = solution.getRoutes().get(solution.getRoutes().size() - 1);
                Integer maxExchangeStation = toVisitSet.stream().findFirst().get();
                double distanceFromPrevious = Double.MAX_VALUE;
                int maxExchange = 0;
                for (Integer station:
                        toVisitSet) {
                    int stationDemand = stations.get(station).getDemand();
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
                        distanceFromPrevious = AlgStation.getDistance(stations.get(lastStationInSolution), graph.getVerticesMap().get(station));
                    }
                    else if(stationExchange == maxExchange){
                        double currentDistanceFromPrevious = AlgStation.getDistance(stations.get(lastStationInSolution), stations.get(station));
                        if(distanceFromPrevious > currentDistanceFromPrevious){
                            maxExchangeStation = station;
                        }
                    }
                }

                int stationDemand = graph.getVerticesMap().get(maxExchangeStation).getDemand();
                int vehicleAction =  stationDemand < 0 ? -currentCapacity : truckCapacity - currentCapacity;
                solution.addNewStation(maxExchangeStation, vehicleAction, truckCapacity - currentCapacity);
                currentCapacity += vehicleAction;
                AlgStation referredStation = graph.getVerticesMap().get(maxExchangeStation);
                referredStation.setStationed(referredStation.getStationed() + vehicleAction);
            }
        }
        solution.addNewStation(0, truckCapacity - currentCapacity, truckCapacity - currentCapacity);

        return solution;
    }

    private List<Tuple<Integer, Double>> sortByDistanceTo(AlgStation startStation, Set<Integer> stations){
        List<Tuple<Integer, Double>> stationDistances = new ArrayList<>();
        for (var station:
             stations) {
            AlgStation endStation = graph.getVerticesMap().get(station);
            double distance = AlgStation.getDistance(startStation, endStation);
            stationDistances.add(new Tuple<>(station, distance));
        }
        return stationDistances.stream().sorted().toList();
    }
}
