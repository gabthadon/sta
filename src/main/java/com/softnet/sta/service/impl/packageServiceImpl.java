package com.softnet.sta.service.impl;

import com.softnet.sta.database.entity.Course;
import com.softnet.sta.database.entity.Packages;
import com.softnet.sta.database.entity.Requirement;
import com.softnet.sta.dto.request.PackageRequest;
import com.softnet.sta.dto.request.RequirementRequest;
import com.softnet.sta.dto.response.PackageResponse;
import com.softnet.sta.exception.NotFoundException;
import com.softnet.sta.mapper.PackageMapper;
import com.softnet.sta.repository.CourseRepository;
import com.softnet.sta.repository.PackageRepository;
import com.softnet.sta.repository.RequirementRepository;
import com.softnet.sta.service.interfaces.PackageService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class packageServiceImpl implements PackageService {
    private final PackageRepository packageRepository;
    private final CourseRepository courseRepository;
    private final RequirementRepository requirementRepository;


    @Transactional
    @Override
    public PackageResponse createPackage(PackageRequest packageRequest) {
        // Create a new package entity
        Packages newPackage = new Packages();
        newPackage.setPackageName(packageRequest.getPackageName());
        newPackage.setPackageDescription(packageRequest.getPackageDescription());
        newPackage.setPackageDuration(packageRequest.getPackageDuration());
        newPackage.setLearningMode(packageRequest.getLearningMode());
        newPackage.setLearningModeDescription(packageRequest.getLearningModeDescription());
        newPackage.setAmount(packageRequest.getAmount());
        newPackage.setRequirements(packageRequest.getRequirements().toString());  // Assuming this is a textual description
        newPackage.setCourseOutcome(packageRequest.getCourseOutcome());
        newPackage.setCourseOutline(packageRequest.getCourseOutline());
        newPackage.setImage1url(packageRequest.getImage1url());
        newPackage.setImage2url(packageRequest.getImage2url());
        newPackage.setImage3url(packageRequest.getImage3url());

        // Retrieve the courses by the provided IDs
        List<Course> courses = courseRepository.findAllById(packageRequest.getCourseIds());

        // Ensure all course IDs are found
        if (courses.size() != packageRequest.getCourseIds().size()) {
            throw new NotFoundException("Some courses not found for the provided IDs", 404);
        }

        // Maintain many-to-many relationship
        newPackage.setCourses(courses);
        courses.forEach(course -> course.getPackages().add(newPackage));

        // Save the package without requirements first to generate a unique package ID
        Packages savedPackage = packageRepository.save(newPackage);

        // Loop over the requirements, save each one, and associate with the package
        for (RequirementRequest req : packageRequest.getRequirements()) {
            Requirement requirement = new Requirement();
            requirement.setPackages(savedPackage);  // Set the relationship
            requirement.setRequirementType(req.getRequirementType());
            requirement.setDescription(req.getDescription());
            requirement.setAmount(req.getAmount());

            // Save each requirement individually to generate unique IDs
            requirementRepository.save(requirement);

            // Add requirement to the package's requirement list
            savedPackage.getRequirement().add(requirement);
        }

        // Save the updated package with the new requirements list
        packageRepository.save(savedPackage);

        // Return the response
        return PackageMapper.topackageResponse(savedPackage);
    }



    @Modifying
    @Transactional
    @Override
    public PackageResponse updatePackage(PackageRequest packageRequest, Long id) {
        // Find the package by ID
        Optional<Packages> packagesOptional = packageRepository.findById(id);
        if (packagesOptional.isEmpty()) {
            throw new NotFoundException("Package with id " + id + " Not Found", 404);
        }

        Packages packageData = packagesOptional.get();
        log.info("Updating package: {}", packageRequest);

        // Update package details
        packageData.setPackageName(packageRequest.getPackageName());
        packageData.setPackageDescription(packageRequest.getPackageDescription());
        packageData.setPackageDuration(packageRequest.getPackageDuration());
        packageData.setAmount(packageRequest.getAmount());
        packageData.setLearningMode(packageRequest.getLearningMode());
        packageData.setCourseOutcome(packageRequest.getCourseOutcome());
        packageData.setCourseOutline(packageRequest.getCourseOutline());
        packageData.setImage1url(packageRequest.getImage1url());
        packageData.setImage2url(packageRequest.getImage2url());
        packageData.setImage3url(packageRequest.getImage3url());

        // Retrieve the courses by the provided IDs
        List<Course> courses = courseRepository.findAllById(packageRequest.getCourseIds());
        if (courses.size() != packageRequest.getCourseIds().size()) {
            throw new NotFoundException("Some courses not found for the provided IDs", 404);
        }

        // Maintain many-to-many relationship
        packageData.getCourses().clear(); // Clear existing courses
        packageData.getCourses().addAll(courses); // Add new courses
        courses.forEach(course -> course.getPackages().add(packageData));

        // Delete existing requirements associated with this package
//        requirementRepository.deleteByPackagesId(id);

        // Add new requirements
        for (RequirementRequest req : packageRequest.getRequirements()) {
            Optional<Requirement> mRequirement = requirementRepository.findById(req.getRequirementId());
            if (mRequirement.isPresent()) {
                Requirement requirement = mRequirement.get();
                requirement.setPackages(packageData); // Set the relationship with the package
                requirement.setRequirementType(req.getRequirementType());
                requirement.setDescription(req.getDescription());
                requirement.setAmount(req.getAmount());

                requirementRepository.save(requirement);

                // Add the requirement to the package's requirement list
//                packageData.getRequirement().add(requirement);
            }

        }

        // Save the updated package with the new requirements
        Packages pkg = packageRepository.save(packageData);

        // Return the updated package response
        return PackageMapper.topackageResponse(pkg);
    }



    @Transactional
    @Override
    public void deletePackage(Long id) {
        Optional<Packages> packages = packageRepository.findById(id);

        if (packages.isEmpty()) {
            throw new NotFoundException("Package with id " + id + " Not Found", 404);
        }

        // Get the package entity
        Packages packageData = packages.get();

        // Clear associations with courses (removes entries from course_package)
        packageData.getCourses().clear();

        // Save the package entity after clearing associations
        packageRepository.save(packageData);

        // Now delete the package
        packageRepository.deletePackageWithRelations(id);

        log.info("Package with id {} deleted successfully", id);
    }



    @Transactional
    @Override
    public PackageResponse getPackageById(Long id) {
        Optional<Packages> packages = packageRepository.findById(id);

        if (packages.isEmpty()) {
            throw new NotFoundException("Package with id " + id + " Not Found", 404);
        }

        Packages packageData = packages.get();

        return PackageMapper.topackageResponse(packageData);
    }

    @Transactional
    @Override
    public List<PackageResponse> getAllPackages() {
        List<Packages> packagesList = packageRepository.findAll();

        return packagesList.stream().map(PackageMapper::topackageResponse).collect(Collectors.toList());

    }

    @Transactional
    @Override
    public List<PackageResponse> getPackagesByCategoryId(Long categoryId) {
        List<Packages> packages = packageRepository.findByCategoryId(categoryId);
        return packages.stream()
                .map(PackageMapper::topackageResponse)
                .collect(Collectors.toList());
    }


}
