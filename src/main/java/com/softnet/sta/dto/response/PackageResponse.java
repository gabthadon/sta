package com.softnet.sta.dto.response;

import com.softnet.sta.database.entity.Course;
import com.softnet.sta.enums.LearningMode;
import com.softnet.sta.enums.RequirementType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PackageResponse {

    private Long packageId;
    private String packageName;
    private String packageDescription;
    private String packageDuration;
    private List<CourseResponse> courses;
    private BigDecimal amount;
    private String courseOutline;
    private String courseOutcome;
    private String requirements;
    private String learningModeDescription;
    private LearningMode learningMode;
    private String image1url;
    private String image2url;
    private String image3url;

    private List<RequirementResponse> requirement;




}
