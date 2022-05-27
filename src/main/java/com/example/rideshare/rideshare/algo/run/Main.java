package com.example.rideshare.rideshare.algo.run;

import com.example.rideshare.rideshare.algo.datastructures.bikesharing.EdgeWeightFitness;
import com.example.rideshare.rideshare.algo.datastructures.bikesharing.FitnessFunction;
import com.example.rideshare.rideshare.algo.datastructures.bikesharing.GreedySolutionGenerator;
import com.example.rideshare.rideshare.algo.datastructures.bikesharing.SolutionGenerator;
import com.example.rideshare.rideshare.algo.datastructures.graph.Graph;
import com.example.rideshare.rideshare.algo.datastructures.model.Station;

import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        int alfa = 1;
        int truckCapacity = 10;
        Graph<Station> stations = new Graph<>(21);
        var vertices = stations.getVerticesMap();

        vertices.put(0,
                new Station(
                        "Depot",
                        ThreadLocalRandom.current().nextDouble(0, 1500),
                        ThreadLocalRandom.current().nextDouble(0, 1500),
                        0, 0, 0));
        for (int i = 1; i < 21; i++) {
            int demand = ThreadLocalRandom.current().nextInt(-10, 11);
            Station station = new Station(
                    String.valueOf(i),
                    ThreadLocalRandom.current().nextDouble(0, 1500),
                    ThreadLocalRandom.current().nextDouble(0, 1500),
                    alfa * 20,
                    alfa * 10,
                    alfa * (10 + demand)
            );
            vertices.put(i, station);
        }

        for (int start = 0; start < 21 - 1; start++) {
            for (int end = start + 1; end < 21; end++) {
                stations.setUndirectedEdge(start, end, Station.getDistance(vertices.get(start), vertices.get(end)));
            }
        }

        SolutionGenerator initialSolGenerator = new GreedySolutionGenerator(stations, truckCapacity);
        FitnessFunction fitnessFunction = new EdgeWeightFitness();

    }
}
