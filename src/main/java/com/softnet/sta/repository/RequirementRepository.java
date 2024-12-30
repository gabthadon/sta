package com.softnet.sta.repository;

import com.softnet.sta.database.entity.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequirementRepository extends JpaRepository<Requirement, Long> {

    List<Requirement> findByPackagesId(Long packageId);



    @Modifying
    @Query(value = "DELETE FROM requirement WHERE packages_id = :packageId", nativeQuery = true)
    void deleteByPackagesId(Long packageId);
}
