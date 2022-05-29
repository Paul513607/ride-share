package com.example.rideshare.rideshare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "STATIONS")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}