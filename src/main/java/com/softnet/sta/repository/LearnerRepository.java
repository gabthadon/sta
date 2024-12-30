package com.softnet.sta.repository;

import com.softnet.sta.database.entity.Learner;
import com.softnet.sta.database.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LearnerRepository extends JpaRepository<Learner, Long> {
    Optional<Learner> findByUsers(Users user);
    List<Learner> findByOrganizationRepId(String orgRepId);
    List<Learner> findByCohortName(String cohortName);
    List<Learner> findByEnrollmentId(String enrollmentId);
}
