package com.example.rideshare.rideshare.algo.datastructures.graph.flow;

import com.example.rideshare.rideshare.algo.datastructures.graph.Graph;

public interface NetworkFlow {
    public MaxFlow getMaximumFlow(Graph graph, int source, int target);
}
