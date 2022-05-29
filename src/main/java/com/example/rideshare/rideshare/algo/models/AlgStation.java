package com.example.rideshare.rideshare.algo.models;

import com.example.rideshare.rideshare.algo.graph.Copyable;
import com.example.rideshare.rideshare.model.Station;
import lombok.Data;

@Data
public class AlgStation extends Station implements Copyable<AlgStation> {
    private String name;
    //coordinates
    private double c_X;
    private double c_Y;
    private int capacity;
    private int stationed;
    private int optimalStationed;

    public AlgStation(String name, double c_X, double c_Y, int capacity, int stationed, int optimalStationed){
        this.capacity = capacity;
        this.c_X = c_X;
        this.stationed = stationed;
        this.c_Y = c_Y;
        this.name = name;
        this.optimalStationed = optimalStationed;
    }

    public AlgStation(AlgStation AlgStation){
        this(AlgStation.name, AlgStation.c_X, AlgStation.c_Y, AlgStation.capacity, AlgStation.stationed, AlgStation.optimalStationed);
    }

    public int getDemand(){
        return optimalStationed - stationed;
    }

    public static double getDistance(AlgStation first, AlgStation second){
        return Math.sqrt(Math.pow(first.c_X - second.c_X, 2) + Math.pow(first.c_Y - second.c_Y, 2));
    }

    public AlgStation copy(){
        return new AlgStation(this);
    }
}
