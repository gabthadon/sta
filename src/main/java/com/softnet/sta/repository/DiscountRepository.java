package com.softnet.sta.repository;

import com.softnet.sta.database.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Optional<Discount> findByCodeAndIsActiveTrue(String code);


    //Get discount
    @Query(value = "SELECT id, code, discount_percentage, name " +
            "FROM discount " +
            "WHERE is_active = true " +
            "AND usage_count < usage_limit " +
            "AND expiry_date >= NOW() " +
            "AND code = :code", nativeQuery = true)
    List<Object[]> applyDiscount(@Param("code") String code);


    Discount findByCode(String code);



}
