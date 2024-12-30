package com.softnet.sta.repository;

import com.softnet.sta.database.entity.Users;
import com.softnet.sta.dto.response.LoginResponse;
import com.softnet.sta.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    boolean existsByEmailAddress(String emailAddress);
    boolean existsByPhoneNumber(String phoneNumber);

    Users findByEmailAddress(String username);

    @Query("SELECT u FROM Users u LEFT JOIN FETCH u.authorities WHERE u.id = :userId")
    Optional<Users> findByIdWithAuthorities(@Param("userId") Long userId);

    List<Users> findByUserRole(UserRole userRole);

    Optional<Users> findByRegistrationId(String registrationId);

    List<Users> findAllByUserRole(UserRole userRole);
}
