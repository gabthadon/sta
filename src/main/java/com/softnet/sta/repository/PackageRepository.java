package com.softnet.sta.repository;

import com.softnet.sta.database.entity.Packages;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface PackageRepository extends JpaRepository<Packages, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM course_package WHERE package_id = :packageId; DELETE FROM packages WHERE id = :packageId", nativeQuery = true)
    void deletePackageWithRelations(@Param("packageId") Long packageId);


    @Query("SELECT p FROM Packages p JOIN p.courses c WHERE c.category.id = :categoryId")
    List<Packages> findByCategoryId(@Param("categoryId") Long categoryId);

}
