package com.softnet.sta.repository;

import com.softnet.sta.database.entity.Cohort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CohortRepository extends JpaRepository<Cohort, Long> {

    @Query(value = "SELECT * FROM cohort WHERE is_active = false", nativeQuery = true)
    List<Cohort> findAllInactiveCohorts();
    @Query(value = "SELECT * FROM cohort WHERE is_active = true", nativeQuery = true)
    List<Cohort> findAllActiveCohorts();

}
