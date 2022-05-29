package com.example.rideshare.rideshare.repositories;

import com.example.rideshare.rideshare.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

}
