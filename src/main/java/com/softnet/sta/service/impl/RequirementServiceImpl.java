package com.softnet.sta.service.impl;

import com.softnet.sta.database.entity.Packages;
import com.softnet.sta.database.entity.Requirement;
import com.softnet.sta.dto.request.RequirementRequest;
import com.softnet.sta.dto.response.RequirementResponse;
import com.softnet.sta.exception.NotFoundException;
import com.softnet.sta.mapper.RequirementMapper;
import com.softnet.sta.repository.PackageRepository;
import com.softnet.sta.repository.RequirementRepository;
import com.softnet.sta.service.interfaces.RequirementService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class RequirementServiceImpl implements RequirementService {
    private RequirementRepository requirementRepository;
    private PackageRepository packageRepository;

    @Override
    public RequirementResponse createRequirement(RequirementRequest requirementRequest) {
        Packages learnerPackage = packageRepository.findById(requirementRequest.getPackageId())
                .orElseThrow(() -> new NotFoundException("Package not found with id: " + requirementRequest.getPackageId(), 404));

        Requirement requirement = new Requirement();
        requirement.setRequirementType(requirementRequest.getRequirementType());
        requirement.setDescription(requirementRequest.getDescription());

        requirement.setAmount(requirementRequest.getAmount());
        requirement.setPackages(learnerPackage);

        requirement = requirementRepository.save(requirement);
        return RequirementMapper.toRequirementResponse(requirement);
    }

    @Override
    public RequirementResponse updateRequirement(Long requirementId, RequirementRequest requirementRequest) {
        Requirement requirement = requirementRepository.findById(requirementId)
                .orElseThrow(() -> new NotFoundException("Requirement not found with id: " + requirementId, 404));

        Packages learnerPackage = packageRepository.findById(requirementRequest.getPackageId())
                .orElseThrow(() -> new NotFoundException("Package not found with id: " + requirementRequest.getPackageId(), 404));

        requirement.setRequirementType(requirementRequest.getRequirementType());
        requirement.setDescription(requirementRequest.getDescription());
        requirement.setAmount(requirementRequest.getAmount());
        requirement.setPackages(learnerPackage);

        requirement = requirementRepository.save(requirement);
        return RequirementMapper.toRequirementResponse(requirement);
    }

    @Override
    public void deleteRequirement(Long requirementId) {
        Requirement requirement = requirementRepository.findById(requirementId)
                .orElseThrow(() -> new NotFoundException("Requirement not found with id: " + requirementId, 404));
        requirementRepository.delete(requirement);
    }

    @Override
    public List<RequirementResponse> getRequirementsByPackageId(Long packageId) {
        List<Requirement> requirements = requirementRepository.findByPackagesId(packageId);
        return requirements.stream()
                .map(RequirementMapper::toRequirementResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RequirementResponse getRequirementById(Long requirementId) {
        Requirement requirement = requirementRepository.findById(requirementId)
                .orElseThrow(() -> new NotFoundException("Requirement not found with id: " + requirementId, 404));

        return RequirementMapper.toRequirementResponse(requirement);
    }


}
