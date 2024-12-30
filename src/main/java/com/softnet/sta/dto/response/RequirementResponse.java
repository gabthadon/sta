package com.softnet.sta.dto.response;

import com.softnet.sta.enums.RequirementType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequirementResponse {

    private Long requirementId;
   private RequirementType requirementType;
    private String description;
    private BigDecimal amount;
    private Long packageId;
}
