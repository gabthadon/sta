package com.softnet.sta.database.entity;

import com.softnet.sta.database.base.BaseEntity;
import com.softnet.sta.enums.DiscountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Discount extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer discountPercentage;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    @Column(nullable = false)
    private Boolean isActive ;

    @Column(nullable = false)
    private Long usageLimit;

    @Column(nullable = false)
    private Long usageCount ;

    /*
   @Column(nullable = true, columnDefinition = )
    private Long packages_id;

    private DiscountType discountType;

     */
}