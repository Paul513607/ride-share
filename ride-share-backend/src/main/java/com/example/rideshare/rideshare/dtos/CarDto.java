package com.example.rideshare.rideshare.dtos;

import com.example.rideshare.rideshare.model.Route;
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
public class CarDto {
    private Long id;
    private Long totalCapacity;
    private Long currentLoad;
    private Route currentRoute;
}
