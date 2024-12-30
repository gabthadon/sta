package com.softnet.sta.database.entity;

import com.softnet.sta.database.base.BaseEntity;
import com.softnet.sta.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Users extends BaseEntity {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String registrationId;
    private String password;
    private Boolean emailVerified = false;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @OneToOne(mappedBy = "users", cascade = CascadeType.ALL)
    private Instructor instructor;

    @OneToOne(mappedBy = "users", cascade = CascadeType.ALL)
    private Learner learner;

    @OneToOne(mappedBy = "users", cascade = CascadeType.ALL)
    private Admin admin;

    @OneToOne(mappedBy = "users", cascade = CascadeType.ALL)
    private OrganizationRep organizationRep;

    // One-to-Many relationship with Authority
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Authority> authorities;


    private String otp;



    private LocalDateTime otpExpiryDate;
}

