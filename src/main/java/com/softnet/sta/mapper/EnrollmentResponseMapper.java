package com.softnet.sta.mapper;

import com.softnet.sta.database.entity.Course;
import com.softnet.sta.database.entity.Learner;
import com.softnet.sta.database.entity.Requirement;
import com.softnet.sta.dto.response.*;
import com.softnet.sta.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EnrollmentResponseMapper {



    public static EnrollmentResponse convertToEnrollmentResponse(Learner learner) {
        EnrollmentResponse response = new EnrollmentResponse();


        if (learner == null) {
          throw new NotFoundException("Learner  not found", 404);
        }



        if (learner.getUsers() != null) {
            SignUpResponse signUpResponse = new SignUpResponse();
            signUpResponse.setId(learner.getUsers().getId());
            signUpResponse.setFullName(learner.getUsers().getFirstName() + " " + learner.getUsers().getLastName());
            signUpResponse.setEmailAddress(learner.getUsers().getEmailAddress());
            signUpResponse.setPhoneNumber(learner.getUsers().getPhoneNumber());
            signUpResponse.setRegistrationId(learner.getUsers().getRegistrationId());
            signUpResponse.setEmailVerified(learner.getUsers().getEmailVerified());
            signUpResponse.setUserRole(learner.getUsers().getUserRole().toString());
            signUpResponse.setOrganization(learner.getOrganization());
            signUpResponse.setJobRole(learner.getUsers().getUserRole().toString());
            response.setSignUpResponse(signUpResponse);
        }


        response.setCountry(learner.getCountry());
        response.setState(learner.getState());
        response.setAddress(learner.getAddress());
        response.setCity(learner.getCity());
        response.setMedFitnessDocUrl(learner.getMedFitnessDocUrl());
        response.setAvailability(learner.getAvailability());
        response.setIdCardUrl(learner.getIdCardUrl());
        response.setIsEnrolled(learner.getIsEnrolled());
        response.setPassportPhotoUrl(learner.getPassportPhotoUrl());
        response.setCohortName(learner.getCohortName());






        if (learner.getPackages() != null) {
            PackageResponse packageResponse = new PackageResponse();
            packageResponse.setPackageId(learner.getPackages().getId());
            packageResponse.setPackageName(learner.getPackages().getPackageName());
            packageResponse.setPackageDescription(learner.getPackages().getPackageDescription());
            packageResponse.setPackageDuration(learner.getPackages().getPackageDuration());
            packageResponse.setAmount(learner.getPackages().getAmount());
            packageResponse.setCourseOutcome(learner.getPackages().getCourseOutcome());
            packageResponse.setImage1url(learner.getPackages().getImage1url());
            packageResponse.setImage2url(learner.getPackages().getImage2url());
            packageResponse.setImage3url(learner.getPackages().getImage3url());
            packageResponse.setRequirements(learner.getPackages().getRequirements());
            packageResponse.setLearningModeDescription(learner.getPackages().getLearningModeDescription());
            packageResponse.setLearningMode(learner.getPackages().getLearningMode());
            packageResponse.setCourseOutline(learner.getPackages().getCourseOutline());
            packageResponse.setRequirement( learner.getPackages().getRequirement().get(0).getId() !=null ?
                    learner.getPackages().getRequirement().stream().map(req->new RequirementResponse(
                    req.getId(),
                    req.getRequirementType(),
                    req.getDescription(),
                    req.getAmount(),
                    req.getPackages().getId()
            )).collect(Collectors.toList()) :
                    new ArrayList<>());



            if (learner.getPackages().getCourses() != null && !learner.getPackages().getCourses().isEmpty()) {
                List<CourseResponse> courseResponseList = learner.getPackages().getCourses().stream()
                        .map(CourseMapper::toCourseResponse)
                        .collect(Collectors.toList());

                packageResponse.setCourses(courseResponseList);
            }

            response.setPackageResponse(packageResponse);


        }
        return response;
    }
}
