package com.example.rideshare.rideshare.algo.datastructures.graph.flow;

import com.example.rideshare.rideshare.algo.datastructures.graph.Graph;
import com.example.rideshare.rideshare.algo.datastructures.model.Copyable;

public interface NetworkFlow<T extends Copyable<T>> {
    public MaxFlow<T> getMaximumFlow(Graph<T> graph, int source, int target);
}
