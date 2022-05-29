package com.example.rideshare.rideshare.repositories;

import com.example.rideshare.rideshare.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {

}
