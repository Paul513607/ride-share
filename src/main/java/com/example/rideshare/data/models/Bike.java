package com.example.rideshare.data.models;

import javax.persistence.*;

@Entity
@Table(name = "BIKE")
public class Bike {
    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "IN_MAINTENANCE")
    private Boolean inMaintenance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "STATION_ID")
    private Station station;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ROUTE_ID")
    private Route route;

    @Column(name = "TIME_USED_SECONDS")
    private Long timeUsedSeconds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getInMaintenance() {
        return inMaintenance;
    }

    public void setInMaintenance(Boolean inMaintenance) {
        this.inMaintenance = inMaintenance;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Long getTimeUsedSeconds() {
        return timeUsedSeconds;
    }

    public void setTimeUsedSeconds(Long timeUsedSeconds) {
        this.timeUsedSeconds = timeUsedSeconds;
    }

}