package com.example.rideshare.rideshare.algo.graph;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Graph<T extends Copyable<T>> {
    private final List<List<Edge>> incomingEdges;
    private final Map<Integer, T> verticesMap;

    public Graph(int size) {
        incomingEdges = Stream.generate(ArrayList<Edge>::new).limit(size).collect(Collectors.toList());
        verticesMap = new HashMap<>(size);

        for (int i = 0; i < size; i++) {
            verticesMap.put(i, null);
        }
    }

    public Graph(Graph<T> g) {
        incomingEdges = g.incomingEdges.stream().map(ArrayList::new).collect(Collectors.toList());
        verticesMap = new HashMap<>(g.verticesMap.size());
        g.getVerticesMap().forEach((key, value) -> verticesMap.put((int)key, value.copy()));
    }

    public Map<Integer, T> getVerticesMap() {
        return verticesMap;
    }

    public boolean hasNoEdge() {
        return IntStream.range(0, size()).allMatch(i -> getIncomingEdges(i).isEmpty());
    }

    public int size() {
        return incomingEdges.size();
    }

    public Edge setDirectedEdge(int source, int target, double weight) {
        List<Edge> edges = getIncomingEdges(target);
        Edge edge = new Edge(source, target, weight);
        edges.add(edge);
        return edge;
    }

    public void setUndirectedEdge(int source, int target, double weight) {
        setDirectedEdge(source, target, weight);
        setDirectedEdge(target, source, weight);
    }

    public List<Edge> getIncomingEdges(int target) {
        return incomingEdges.get(target);
    }

    public Edge getEdge(int source, int target){
        return incomingEdges.get(target).stream().filter(edge -> edge.getSource() == source).findAny().orElse(null);
    }

    public List<Edge> getAllEdges() {
        return incomingEdges.stream().flatMap(List::stream).collect(Collectors.toList());
    }

    public Deque<Integer> getVerticesWithNoIncomingEdges() {
        return IntStream.range(0, size()).filter(i -> getIncomingEdges(i).isEmpty()).boxed().collect(Collectors.toCollection(ArrayDeque::new));
    }

    public List<Deque<Edge>> getOutgoingEdges() {
        List<Deque<Edge>> outgoing_edges = Stream.generate(ArrayDeque<Edge>::new).limit(size()).collect(Collectors.toList());

        for (int target = 0; target < size(); target++) {
            for (Edge incoming_edge : getIncomingEdges(target))
                outgoing_edges.get(incoming_edge.getSource()).add(incoming_edge);
        }

        return outgoing_edges;
    }
}
