package com.example.rideshare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Station {
    private Long id;
    private String name;
    private Double coordinatesX;
    private Double coordinatesY;
    private Long totalBikeCapacity;
    private Long bikesStationed;
    private Long optimalBikeCount;
    private boolean isDepo;

    public Double getX() {
        return coordinatesX;
    }

    public void setX(Double coordinatesX) {
        this.coordinatesX = coordinatesX;
    }

    public Double getY() {
        return coordinatesY;
    }

    public void setY(Double coordinatesY) {
        this.coordinatesY = coordinatesY;
    }
}
