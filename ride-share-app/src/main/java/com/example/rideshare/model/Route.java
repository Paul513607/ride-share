package com.example.rideshare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Route {
    private Long id;
    private Station stationSrc;
    private Station stationDest;
    private Long length;

    public Route(Long id, Station s1, Station s2) {
        this.id = id;
        this.stationSrc = s1;
        this.stationDest = s2;
        this.length = (long) Math.sqrt((s1.getX() - s2.getX()) * (s1.getX() - s2.getX()) +
                        (s1.getY() - s2.getY()) * (s1.getY() - s2.getY()));
    }

    public boolean containsNode(Station station) {
        return stationSrc.equals(station) || stationDest.equals(station);
    }
}
