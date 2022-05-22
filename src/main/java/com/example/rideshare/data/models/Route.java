package com.example.rideshare.data.models;

import javax.persistence.*;

@Entity
@Table(name = "ROUTE")
public class Route {
    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "STATION_SRC")
    private Station stationSrc;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "STATION_DEST")
    private Station stationDest;

    @Column(name = "LENGTH")
    private Long length;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Station getStationSrc() {
        return stationSrc;
    }

    public void setStationSrc(Station stationSrc) {
        this.stationSrc = stationSrc;
    }

    public Station getStationDest() {
        return stationDest;
    }

    public void setStationDest(Station stationDest) {
        this.stationDest = stationDest;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

}