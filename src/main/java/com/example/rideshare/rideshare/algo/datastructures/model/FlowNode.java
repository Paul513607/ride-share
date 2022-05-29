package com.example.rideshare.rideshare.algo.datastructures.model;

import com.example.rideshare.rideshare.algo.datastructures.graph.Edge;
import lombok.Data;

import java.util.Objects;

public@Data
class FlowNode implements Copyable<FlowNode>{
    private Integer nodeInGraph;
    private Integer nrOfOccurrence;

    public FlowNode(int nodeInGraph, int nrOfOccurrence){
        this.nodeInGraph = nodeInGraph;
        this.nrOfOccurrence = nrOfOccurrence;
    }
    @Override
    public FlowNode copy() {
        return new FlowNode(this.nodeInGraph, this.nrOfOccurrence);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlowNode node = (FlowNode) o;
        return Objects.equals(nodeInGraph, node.getNodeInGraph());
    }
}
