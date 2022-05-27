package com.example.rideshare.rideshare.algo.datastructures.graph.flow;

import com.example.rideshare.rideshare.algo.datastructures.graph.Graph;

public interface NetworkFlow<T> {
    public MaxFlow<T> getMaximumFlow(Graph<T> graph, int source, int target);
}
