package com.softnet.sta.database.entity;

import com.softnet.sta.database.base.BaseEntity;
import com.softnet.sta.enums.LearningMode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Packages extends BaseEntity {

    private String packageName;

 @Column(columnDefinition = "TEXT")
    private String packageDescription;

    private String packageDuration;

    @ManyToMany( cascade = CascadeType.ALL)
    @JoinTable(
            name = "course_package",
            joinColumns = @JoinColumn(name = "package_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;

    private BigDecimal amount;

   @Column(columnDefinition = "TEXT")
    private String learningModeDescription;

    private LearningMode learningMode;

    @Column(columnDefinition = "TEXT")
    private String courseOutline;

 @Column(columnDefinition = "TEXT")
    private String courseOutcome;

 @Column(columnDefinition = "TEXT")
    private String requirements;

    @Column(nullable = false)
    private String image1url;

    @Column(nullable = false)
    private String image2url;

    @Column(nullable = false)
    private String image3url;

    @OneToMany(mappedBy = "packages", fetch = FetchType.LAZY)
    private List<Learner> learner;


    @OneToMany(mappedBy = "packages", cascade = CascadeType.ALL)
    private List<Requirement> requirement = new ArrayList<>();
}
