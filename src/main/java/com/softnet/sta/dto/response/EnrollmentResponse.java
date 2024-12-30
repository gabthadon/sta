package com.softnet.sta.dto.response;

import lombok.Data;

@Data
public class EnrollmentResponse {

    private  SignUpResponse signUpResponse;

    private String country;
    private String state;
    private String address;
    private String city;
    private  String passportPhotoUrl;
    private  String medFitnessDocUrl;
    private  String availability;
    private String idCardUrl;
    private Boolean isEnrolled;
    private String cohortName;
    private Boolean isRequirementMet;

    private PackageResponse packageResponse;
}
