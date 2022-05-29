package com.example.rideshare.rideshare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "CARS")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}