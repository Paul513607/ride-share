package com.example.rideshare.rideshare.dtos;

import com.example.rideshare.rideshare.model.Route;
import com.example.rideshare.rideshare.model.Station;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BikeDto {
    private Long id;
    private Boolean inMaintenance;
    private Station station;
    private Route route;
    private Long timeUsedSeconds;
}
