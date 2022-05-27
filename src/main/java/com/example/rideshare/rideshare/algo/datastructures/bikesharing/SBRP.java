package com.example.rideshare.rideshare.algo.datastructures.bikesharing;

import com.example.rideshare.rideshare.algo.datastructures.graph.Graph;
import com.example.rideshare.rideshare.algo.datastructures.model.Copyable;

public interface SBRP {
    public<T extends Copyable<T>> SBRPSolution getRepositioningPath(Graph<T> graph);
}
