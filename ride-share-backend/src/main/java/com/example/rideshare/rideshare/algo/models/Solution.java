package com.example.rideshare.rideshare.algo.models;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    private List<Integer> route;
    private List<Integer> vehicleOperations;
    private List<Integer> vehicleLoad;

    public Solution(List<Integer> route, List<Integer> vehicleOperations, List<Integer> vehicleLoad){
        this.route = route;
        this.vehicleOperations = vehicleOperations;
        this.vehicleLoad = vehicleLoad;
    }

    public Solution(){
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public Solution(Solution solution){
        this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        route.addAll(solution.getRoutes());
        vehicleOperations.addAll(solution.getVehicleOperations());
        vehicleLoad.addAll(solution.getVehicleLoad());
    }

    public void addNewStation(int routeStop, int vehicleOperation, int load){
        route.add(routeStop);
        vehicleOperations.add(vehicleOperation);
        vehicleLoad.add(load);
    }

    public void addToFront(int routeStop, int vehicleOperation, int load){
        route.add(0, routeStop);
        vehicleOperations.add(0, vehicleOperation);
        vehicleLoad.add(0, load);
    }

    public List<Integer> getRoutes() {
        return route;
    }

    public List<Integer> getVehicleLoad() {
        return vehicleLoad;
    }

    public List<Integer> getVehicleOperations() {
        return vehicleOperations;
    }
}
