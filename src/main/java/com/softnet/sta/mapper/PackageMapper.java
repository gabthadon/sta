package com.softnet.sta.mapper;


import com.softnet.sta.database.entity.Packages;
import com.softnet.sta.database.entity.Requirement;
import com.softnet.sta.dto.response.CourseResponse;
import com.softnet.sta.dto.response.PackageResponse;
import com.softnet.sta.dto.response.RequirementResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PackageMapper {

    public static PackageResponse topackageResponse(Packages pkg) {
        List<CourseResponse> courseData = pkg.getCourses().stream()
                .map(course -> new CourseResponse(
                        course.getId(),
                        course.getCourseName(),
                        course.getCourseCode(),
                        course.getCourseDescription(),
                        course.getCategory().getName(),
                        course.getCategory().getId()))
                .collect(Collectors.toList());

        // Mapping requirements with null check
        List<RequirementResponse> requirementList = (pkg.getRequirement() != null) ?
                pkg.getRequirement().stream()
                        .map(RequirementMapper::toRequirementResponse)
                        .collect(Collectors.toList()) :
                new ArrayList<>();  // Handle null by returning an empty list

        return new PackageResponse(
                pkg.getId(),
                pkg.getPackageName(),
                pkg.getPackageDescription(),
                pkg.getPackageDuration(),
                courseData,
                pkg.getAmount(),
                pkg.getCourseOutline(),
                pkg.getCourseOutcome(),
                pkg.getRequirements(),
                pkg.getLearningModeDescription(),
                pkg.getLearningMode(),
                pkg.getImage1url(),
                pkg.getImage2url(),
                pkg.getImage3url(),
                requirementList
        );
    }
}
