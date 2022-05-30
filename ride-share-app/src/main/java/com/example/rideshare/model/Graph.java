package com.example.rideshare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Graph {
    private Set<Station> nodes = new HashSet<>();
    private Set<Route> edges = new HashSet<>();

    public void addNode(Station station) {
        for (Station station1 : nodes) {
            edges.add(new Route(0L, station, station1));
        }
        nodes.add(station);
    }

    public Station findStationById(Long id) {
        return nodes.stream()
                .filter(node -> node.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Station findStationByCoords(Double x, Double y) {
        return nodes.stream()
                .filter(node -> node.getX().equals(x) && node.getY().equals(y))
                .findFirst()
                .orElse(null);
    }

    public Route findRouteForNodes(Station s1, Station s2) {
        return edges.stream()
                .filter(edge -> edge.containsNode(s1) && edge.containsNode(s2))
                .findFirst()
                .orElse(null);
    }

    public Station findDepot() {
        return nodes.stream()
                .filter(Station::isDepo)
                .findFirst()
                .orElse(null);
    }
}
