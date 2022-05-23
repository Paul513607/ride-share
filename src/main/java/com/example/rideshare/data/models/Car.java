package com.example.rideshare.data.models;

import javax.persistence.*;

@Entity
@Table(name = "CAR")
public class Car {
    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "TOTAL_CAPACITY")
    private Long totalCapacity;

    @Column(name = "CURRENT_LOAD")
    private Long currentLoad;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CURRENT_ROUTE")
    private Route currentRoute;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(Long totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public Long getCurrentLoad() {
        return currentLoad;
    }

    public void setCurrentLoad(Long currentLoad) {
        this.currentLoad = currentLoad;
    }

    public Route getCurrentRoute() {
        return currentRoute;
    }

    public void setCurrentRoute(Route currentRoute) {
        this.currentRoute = currentRoute;
    }

}