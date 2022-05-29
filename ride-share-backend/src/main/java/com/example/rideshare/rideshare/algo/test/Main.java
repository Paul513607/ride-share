package com.example.rideshare.rideshare.algo.test;

import com.example.rideshare.rideshare.algo.graph.Graph;
import com.example.rideshare.rideshare.algo.models.AlgStation;
import com.example.rideshare.rideshare.algo.models.Solution;
import com.example.rideshare.rideshare.algo.solver.GreedySBRPSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        List<AlgStation> stations = Arrays.asList(
                new AlgStation("Depot",
                        ThreadLocalRandom.current().nextDouble(0, 1500),
                ThreadLocalRandom.current().nextDouble(0, 1500),
                        0,0,0),
                new AlgStation("S1",
                        ThreadLocalRandom.current().nextDouble(0, 1500),
                        ThreadLocalRandom.current().nextDouble(0, 1500),
                        22,10,15),
                new AlgStation("S2",
                        ThreadLocalRandom.current().nextDouble(0, 1500),
                        ThreadLocalRandom.current().nextDouble(0, 1500),
                        15,10,5),
                new AlgStation("S3",
                        ThreadLocalRandom.current().nextDouble(0, 1500),
                        ThreadLocalRandom.current().nextDouble(0, 1500),
                        30, 12,5),
                new AlgStation("S4",
                        ThreadLocalRandom.current().nextDouble(0, 1500),
                        ThreadLocalRandom.current().nextDouble(0, 1500),
                        20, 15,11),
                new AlgStation("S4",
                        ThreadLocalRandom.current().nextDouble(0, 1500),
                        ThreadLocalRandom.current().nextDouble(0, 1500),
                        35, 15,26)
        );

        Graph<AlgStation> graph = new Graph<>(stations.size());

        for (int i = 0; i < stations.size(); i++) {
            graph.getVerticesMap().put(i, stations.get(i));
        }

        for (int start = 0; start < stations.size() - 1; start++) {
            for (int end = start + 1; end < stations.size(); end++) {
                graph.setUndirectedEdge(start, end, AlgStation.getDistance(stations.get(start), stations.get(end)));
            }
        }

        int truckCapacity = 30;

        GreedySBRPSolver solver = new GreedySBRPSolver(graph, truckCapacity);

        Solution sol = solver.getTruckRoute();
        System.out.println("Station\tAction\tLoadBefore\n");
        for (int i = 0; i < sol.getRoutes().size(); i++) {
            System.out.println(sol.getRoutes().get(i) + "\t" + sol.getVehicleOperations().get(i) + "\t" + sol.getVehicleLoad().get(i) + "\n");
        }
    }
}
