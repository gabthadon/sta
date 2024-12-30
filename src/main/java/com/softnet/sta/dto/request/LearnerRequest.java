package com.softnet.sta.dto.request;

import com.softnet.sta.database.entity.Packages;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class LearnerRequest {

    private Long userId;
    private String country;
    private String state;
    private String address;
    private String city;
    private  String medFitnessDocUrl;
    private  String availability;
    private String cohortName;
    private String passportPhotoUrl;
    private String idCardUrl;
    private Boolean isRequirementMet;
    private Boolean isEnrolled;
    private Long packageId;



}
