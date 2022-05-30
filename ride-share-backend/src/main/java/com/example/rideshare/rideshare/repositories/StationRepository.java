package com.example.rideshare.rideshare.repositories;

import com.example.rideshare.rideshare.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository

public interface StationRepository extends JpaRepository<Station, Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "call public.init_tables()", nativeQuery = true)
    void initProblem();
}
