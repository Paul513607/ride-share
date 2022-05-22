package com.example.rideshare.data.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "STATION")
public class Station {
    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    @Column(name = "COORDINATES_X", precision = 8, scale = 2)
    private BigDecimal coordinatesX;

    @Column(name = "COORDINATES_Y", precision = 8, scale = 2)
    private BigDecimal coordinatesY;

    @Column(name = "TOTAL_BIKE_CAPACITY")
    private Long totalBikeCapacity;

    @Column(name = "BIKES_STATIONED")
    private Long bikesStationed;

    @Column(name = "OPTIMAL_BIKE_COUNT")
    private Long optimalBikeCount;

    @Column(name = "IS_DEPO")
    private Boolean isDepo;

    @Column(name = "TOTAL_CARS")
    private Long totalCars;

    @Column(name = "CARS_STATIONED")
    private Long carsStationed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCoordinatesX() {
        return coordinatesX;
    }

    public void setCoordinatesX(BigDecimal coordinatesX) {
        this.coordinatesX = coordinatesX;
    }

    public BigDecimal getCoordinatesY() {
        return coordinatesY;
    }

    public void setCoordinatesY(BigDecimal coordinatesY) {
        this.coordinatesY = coordinatesY;
    }

    public Long getTotalBikeCapacity() {
        return totalBikeCapacity;
    }

    public void setTotalBikeCapacity(Long totalBikeCapacity) {
        this.totalBikeCapacity = totalBikeCapacity;
    }

    public Long getBikesStationed() {
        return bikesStationed;
    }

    public void setBikesStationed(Long bikesStationed) {
        this.bikesStationed = bikesStationed;
    }

    public Long getOptimalBikeCount() {
        return optimalBikeCount;
    }

    public void setOptimalBikeCount(Long optimalBikeCount) {
        this.optimalBikeCount = optimalBikeCount;
    }

    public Boolean getIsDepo() {
        return isDepo;
    }

    public void setIsDepo(Boolean isDepo) {
        this.isDepo = isDepo;
    }

    public Long getTotalCars() {
        return totalCars;
    }

    public void setTotalCars(Long totalCars) {
        this.totalCars = totalCars;
    }

    public Long getCarsStationed() {
        return carsStationed;
    }

    public void setCarsStationed(Long carsStationed) {
        this.carsStationed = carsStationed;
    }

}