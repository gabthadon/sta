package com.softnet.sta.dto.request;

import lombok.Data;

@Data
public class InstructorRequest {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String bio;
    private String profileImageUrl;

}
