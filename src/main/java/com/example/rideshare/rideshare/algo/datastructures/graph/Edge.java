package com.example.rideshare.rideshare.algo.datastructures.graph;

import java.util.Objects;

public class Edge implements Comparable<Edge> {
    private int source;
    private int target;
    private double weight;

    public Edge(int source, int target, double weight) {
        init(source, target, weight);
    }

    public Edge(int source, int target) {
        this(source, target, 0);
    }

    public Edge(Edge edge) {
        this(edge.getSource(), edge.getTarget(), edge.getWeight());
    }

    private void init(int source, int target, double weight) {
        setSource(source);
        setTarget(target);
        setWeight(weight);
    }

    public int getSource() { return source; }
    public int getTarget() { return target; }
    public double getWeight() { return weight; }

    public void setSource(int vertex) { source = vertex; }
    public void setTarget(int vertex) { target = vertex; }
    public void setWeight(double weight) { this.weight = weight; }
    public void addWeight(double weight) { this.weight += weight; }

    @Override
    public int compareTo(Edge edge) {
        double diff = weight - edge.weight;
        if (diff > 0) return 1;
        else if (diff < 0) return -1;
        else return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return source == edge.source && target == edge.target;
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, target);
    }
}