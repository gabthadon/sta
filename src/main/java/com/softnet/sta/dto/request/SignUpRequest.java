package com.softnet.sta.dto.request;


import com.softnet.sta.enums.UserRole;
import lombok.Data;

@Data
public class SignUpRequest {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String password;
    private UserRole role;
    private String organization;
    private String jobRole;
}
