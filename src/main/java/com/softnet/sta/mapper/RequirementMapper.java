package com.softnet.sta.mapper;

import com.softnet.sta.database.entity.Requirement;
import com.softnet.sta.dto.response.RequirementResponse;

public class RequirementMapper {

    public static RequirementResponse toRequirementResponse(Requirement requirement) {

        RequirementResponse requirementResponse = new RequirementResponse(
                requirement.getId(),
                requirement.getRequirementType(),
                requirement.getDescription(),
                requirement.getAmount(),
                requirement.getPackages().getId()
        );

        return requirementResponse;
    }
}
