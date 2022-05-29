package com.example.rideshare.rideshare.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StationDto {
    private Long id;
    private String name;
    private BigDecimal coordinatesX;
    private BigDecimal coordinatesY;
    private Long totalBikeCapacity;
    private Long bikesStationed;
    private Long optimalBikeCount;
    private boolean isDepo;
}
