package com.example.rideshare.rideshare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "BIKE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}