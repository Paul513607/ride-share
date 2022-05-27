package com.example.rideshare.rideshare.algo.datastructures.bikesharing;

import com.example.rideshare.rideshare.algo.datastructures.graph.Graph;

public interface PerturbationFunc {
    public void perturb(Graph graph, SBRPSolution solution);
}
