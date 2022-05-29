package com.example.rideshare.rideshare.algo.datastructures.graph.flow;

import com.example.rideshare.rideshare.algo.datastructures.graph.Edge;
import com.example.rideshare.rideshare.algo.datastructures.graph.Graph;
import com.example.rideshare.rideshare.algo.datastructures.model.Copyable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaxFlow<T extends Copyable<T>> {
    private Map<Edge, Double> flow_map;
    private double maxflow;

    public MaxFlow(Graph<T> graph) {
        init(graph);
    }

    public void init(Graph<T> graph) {
        flow_map = new HashMap<>();
        maxflow = 0;

        for (Edge edge : graph.getAllEdges())
            flow_map.put(edge, 0d);
    }

    public double getMaxFlow() {
        return maxflow;
    }

    public Map<Edge, Double> getFlow_map() {
        return flow_map;
    }

    public double getResidual(Edge edge) {
        return edge.getWeight() - flow_map.get(edge);
    }

    public void updateFlow(List<Edge> path, double flow) {
        path.forEach(e -> updateFlow(e, flow));
        maxflow += flow;
    }

    public void updateFlow(Edge edge, double flow) {
        Double prev = flow_map.getOrDefault(edge, 0d);
        flow_map.put(edge, prev + flow);
    }


}
