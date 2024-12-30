package com.softnet.sta.dto.request;

import com.softnet.sta.database.entity.Packages;
import com.softnet.sta.enums.RequirementType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class RequirementRequest {
    private RequirementType requirementType;
    private String description;
    private BigDecimal amount;
    private Long packageId;
    private Long requirementId;
}