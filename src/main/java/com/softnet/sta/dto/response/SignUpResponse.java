package com.softnet.sta.dto.response;


import com.softnet.sta.enums.UserRole;
import lombok.Data;

import java.util.List;

@Data
public class SignUpResponse {
    private Long id;
    private String fullName;
    private String emailAddress;
    private String phoneNumber;
    private String registrationId;
    private Boolean emailVerified;
    private String userRole;
    private List<AuthorityResponse> userAuthorities;
    private String organization;
    private String jobRole;

}
