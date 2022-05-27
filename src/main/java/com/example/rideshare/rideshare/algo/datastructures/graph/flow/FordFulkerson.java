package com.example.rideshare.rideshare.algo.datastructures.graph.flow;

import com.example.rideshare.rideshare.algo.datastructures.graph.Edge;
import com.example.rideshare.rideshare.algo.datastructures.graph.Graph;
import com.example.rideshare.rideshare.algo.datastructures.graph.flow.MaxFlow;
import com.example.rideshare.rideshare.algo.datastructures.graph.flow.NetworkFlow;
import com.example.rideshare.rideshare.algo.datastructures.graph.flow.Subgraph;

import java.util.List;

public class FordFulkerson implements NetworkFlow {
    public MaxFlow getMaximumFlow(Graph graph, int source, int target) {
        MaxFlow mf = new MaxFlow(graph);
        Subgraph sub;

        while ((sub = getAugmentingPath(graph, mf, new Subgraph(), source, target)) != null) {
            double min = getMin(mf, sub.getEdges());
            mf.updateFlow(sub.getEdges(), min);
            updateBackward(graph, sub, mf, min);
        }

        return mf;
    }

    private double getMin(MaxFlow mf, List<Edge> path) {
        double min = mf.getResidual(path.get(0));

        for (int i = 1; i < path.size(); i++)
            min = Math.min(min, mf.getResidual(path.get(i)));

        return min;
    }

    private Subgraph getAugmentingPath(Graph graph, MaxFlow mf, Subgraph sub, int source, int target) {
        if (source == target) return sub;
        Subgraph tmp;

        for (Edge edge : graph.getIncomingEdges(target)) {
            if (sub.contains(edge.getSource())) continue;
            if (mf.getResidual(edge) <= 0) continue;
            tmp = new Subgraph(sub);
            tmp.addEdge(edge);
            tmp = getAugmentingPath(graph, mf, tmp, source, edge.getSource());
            if (tmp != null) return tmp;
        }

        return null;
    }

    protected void updateBackward(Graph graph, Subgraph sub, MaxFlow mf, double min) {
        boolean found;

        for (Edge edge : sub.getEdges()) {
            found = false;

            for (Edge rEdge : graph.getIncomingEdges(edge.getSource())) {
                if (rEdge.getSource() == edge.getTarget()) {
                    mf.updateFlow(rEdge, -min);
                    found = true;
                    break;
                }
            }

            if (!found) {
                Edge rEdge = graph.setDirectedEdge(edge.getTarget(), edge.getSource(), 0);
                mf.updateFlow(rEdge, -min);
            }
        }
    }
}
