package com.softnet.sta.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class InstructorProfileResponse {
    private SignUpResponse basicUserDetails;
    private String bio;
    private String profileImageUrl;
    private List<CourseResponse> courseDetails;

}
