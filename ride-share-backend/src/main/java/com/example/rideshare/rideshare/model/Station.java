package com.example.rideshare.rideshare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "STATIONS")
@Data
@NamedStoredProcedureQuery(name = "Station.initProblem",procedureName = "INIT_TABLES", parameters = {
        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "placeholder", type = Integer.class)
})
@NoArgsConstructor
@AllArgsConstructor
public class Station {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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