package com.example.rideshare.rideshare.algo.datastructures.model;

import lombok.Data;

public @Data
class Station implements Copyable<Station> {
    private String name;
    private double coordsX;
    private double coordsY;
    private int capacity;
    private int stationed;
    private int stationedAfterServicing;

    public Station(String name, double coordsX, double coordsY, int capacity, int stationed, int stationedAfterServicing){
        this.capacity = capacity;
        this.coordsX = coordsX;
        this.stationed = stationed;
        this.coordsY = coordsY;
        this.name = name;
        this.stationedAfterServicing = stationedAfterServicing;
    }

    public Station(Station station){
        this(station.name, station.coordsX, station.coordsY, station.capacity, station.stationed, station.stationedAfterServicing);
    }

    public int getDemand(){
        return stationedAfterServicing - stationed;
    }

    public static double getDistance(Station first, Station second){
        return Math.sqrt(Math.pow(first.coordsX - second.coordsX, 2) + Math.pow(first.coordsY - second.coordsY, 2));
    }

    public Station copy(){
        Station s = new Station(this);

        return  s;
    }
}
