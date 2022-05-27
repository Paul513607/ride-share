package com.example.rideshare.rideshare.algo.datastructures.bikesharing;

import java.util.ArrayList;
import java.util.List;

public class SBRPSolution {
    private List<Integer> routes;
    private List<Integer> vehicleOperations;
    private List<Integer> vehicleLoads;

    public SBRPSolution(List<Integer> routes, List<Integer> vehicleOperations, List<Integer> vehicleLoads){
        this.routes = routes;
        this.vehicleOperations = vehicleOperations;
        this.vehicleLoads = vehicleLoads;
    }

    public SBRPSolution(){
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public SBRPSolution(SBRPSolution solution){
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        routes.addAll(solution.getRoutes());
        vehicleOperations.addAll(solution.getVehicleOperations());
        vehicleLoads.addAll(solution.getVehicleLoads());
    }

    public void addNewStation(int routeStop, int vehicleOperation, int vehicleLoad){
        routes.add(routeStop);
        vehicleOperations.add(vehicleOperation);
        vehicleLoads.add(vehicleLoad);
    }

    public List<Integer> getRoutes() {
        return routes;
    }

    public List<Integer> getVehicleLoads() {
        return vehicleLoads;
    }

    public List<Integer> getVehicleOperations() {
        return vehicleOperations;
    }
}
