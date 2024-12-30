package com.softnet.sta.dto.request;

import com.softnet.sta.database.entity.Course;
import com.softnet.sta.database.entity.Payment;
import com.softnet.sta.enums.LearningMode;
import com.softnet.sta.enums.RequirementType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class PackageRequest {
    private String packageName;
    private String packageDescription;
    private String packageDuration;
    private String learningModeDescription;
    private LearningMode learningMode;
    private BigDecimal amount;
    private List<Long> courseIds;
    private String courseOutline;
    private String courseOutcome;
    private List<RequirementRequest> requirements;  // List of requirement requests
    private String image1url;
    private String image2url;
    private String image3url;
}
