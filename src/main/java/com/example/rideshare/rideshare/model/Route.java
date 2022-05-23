package com.example.rideshare.rideshare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ROUTE")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}