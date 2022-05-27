package com.example.rideshare.rideshare.algo.datastructures.bikesharing;

import com.example.rideshare.rideshare.algo.datastructures.graph.Edge;
import com.example.rideshare.rideshare.algo.datastructures.graph.Graph;

public class EdgeWeightFitness implements FitnessFunction{
    @Override
    public<T> double fitness(SBRPSolution solution, Graph<T> graph) {
        double sumEdges = 0;
        var routes = solution.getRoutes();

        for (int i = 1; i < routes.size(); i++) {
            Edge edge = graph.getEdge(routes.get(i-1), routes.get(i));
            if(edge == null) return Double.MAX_VALUE;

            sumEdges += edge.getWeight();
        }

        return  sumEdges;
    }
}
