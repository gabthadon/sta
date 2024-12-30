package com.softnet.sta.service.interfaces;

import com.softnet.sta.dto.request.RequirementRequest;
import com.softnet.sta.dto.response.RequirementResponse;

import java.util.List;

public interface RequirementService {
    RequirementResponse createRequirement(RequirementRequest requirementRequest);

    RequirementResponse updateRequirement(Long requirementId, RequirementRequest requirementRequest);

    void deleteRequirement(Long requirementId);

    List<RequirementResponse> getRequirementsByPackageId(Long packageId);

    RequirementResponse getRequirementById(Long requirementId);
}
