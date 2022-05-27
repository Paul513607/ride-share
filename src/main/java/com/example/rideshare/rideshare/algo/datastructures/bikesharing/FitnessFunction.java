package com.example.rideshare.rideshare.algo.datastructures.bikesharing;

import com.example.rideshare.rideshare.algo.datastructures.graph.Graph;

public interface FitnessFunction {
    public<T> double fitness(SBRPSolution solution, Graph<T> graph);
}
