package com.softnet.sta.repository;

import com.softnet.sta.database.entity.OrganizationRep;
import com.softnet.sta.database.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<OrganizationRep, Long> {
    Optional<OrganizationRep> findByUsers(Users user);
}
