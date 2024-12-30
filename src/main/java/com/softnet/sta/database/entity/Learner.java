package com.softnet.sta.database.entity;

import com.softnet.sta.database.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Learner extends BaseEntity {
        private String organization;
        private String organizationRepId;
        private String profileImageUrl;

        @OneToOne
        @JoinColumn(name = "user_id")
        private Users users;
        private String enrollmentId;
        private String country;
        private String state;
        private String address;
        private String city;
        private String cohortName;
        private String passportPhotoUrl;
        private  String medFitnessDocUrl;
        private  String availability;
        private String idCardUrl;
        private Boolean isEnrolled;
        @Column(columnDefinition = "BOOLEAN DEFAULT false")
        private Boolean hasCompletedTraining;
        @ManyToOne
        private Packages packages;
        private String timeSlot;







}
