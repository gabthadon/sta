package com.softnet.sta.mapper;

import com.softnet.sta.database.entity.Course;
import com.softnet.sta.database.entity.Instructor;
import com.softnet.sta.dto.response.CourseResponse;
import com.softnet.sta.dto.response.InstructorProfileResponse;
import com.softnet.sta.dto.response.SignUpResponse;

import java.util.List;
import java.util.stream.Collectors;

public class InstructorMapper {

    public static InstructorProfileResponse toInstructorProfileResponse(Instructor instructor) {
        return new InstructorProfileResponse(
                toSignUpResponse(instructor),
                instructor.getBio(),
                instructor.getProfileImageUrl(),
                toCourseResponses(instructor.getCourse())
        );

    }




    private static SignUpResponse toSignUpResponse(Instructor instructor) {
        if (instructor.getUsers() == null) {
            return null;  // Handle null case
        }

        SignUpResponse signUpResponse = new SignUpResponse();
        signUpResponse.setId(instructor.getUsers().getId());
        signUpResponse.setFullName(instructor.getUsers().getFirstName() + " " + instructor.getUsers().getLastName());
        signUpResponse.setEmailAddress(instructor.getUsers().getEmailAddress());
        signUpResponse.setPhoneNumber(instructor.getUsers().getPhoneNumber());
        signUpResponse.setRegistrationId(instructor.getUsers().getRegistrationId());
        signUpResponse.setEmailVerified(instructor.getUsers().getEmailVerified());
        signUpResponse.setUserRole(instructor.getUsers().getUserRole().name());
        return signUpResponse;
    }

    private static List<CourseResponse> toCourseResponses(List<Course> courses) {
        if (courses == null || courses.isEmpty()) {
            return null;  // Handle case where no courses are present
        }

        return courses.stream()
                .map(InstructorMapper::toCourseResponse)
                .collect(Collectors.toList());
    }

    private static CourseResponse toCourseResponse(Course course) {
        if (course == null) {
            return null;
        }

        return new CourseResponse(
                course.getId(),
                course.getCourseName(),
                course.getCourseCode(),
                course.getCourseDescription(),
                course.getCategory() != null ? course.getCategory().getName() : null,  // Category name if present
                course.getCategory() != null ? course.getCategory().getId() : null
        );
    }
}
