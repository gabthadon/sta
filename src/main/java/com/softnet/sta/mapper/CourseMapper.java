package com.softnet.sta.mapper;

import com.softnet.sta.database.entity.Course;
import com.softnet.sta.dto.response.CourseResponse;

public class CourseMapper {

    public static CourseResponse toCourseResponse(Course course) {
        return new CourseResponse(
                course.getId(),
                course.getCourseName(),
                course.getCourseCode(),
                course.getCourseDescription(),
                course.getCategory().getName(),
                course.getCategory().getId()
        );
    }
}
