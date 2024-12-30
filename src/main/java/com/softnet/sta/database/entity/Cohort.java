package com.softnet.sta.database.entity;


import com.softnet.sta.database.base.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Cohort extends BaseEntity {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isActive;
}
