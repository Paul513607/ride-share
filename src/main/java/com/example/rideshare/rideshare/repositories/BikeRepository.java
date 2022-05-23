package com.example.rideshare.rideshare.repositories;

import com.example.rideshare.rideshare.model.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

@Repository
public interface BikeRepository extends JpaRepository<Bike, Long> {
    @Procedure(procedureName = "TESTP")
    int testP();
}
