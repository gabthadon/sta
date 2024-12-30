package com.softnet.sta.service.interfaces;

import com.softnet.sta.database.entity.Course;
import com.softnet.sta.dto.request.CourseRequest;
import com.softnet.sta.dto.response.CourseResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface CourseService {
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    CourseResponse createCourse(CourseRequest courseRequest);
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    CourseResponse updateCourse(CourseRequest courseRequest, Long id);

    List<CourseResponse> getAllCourses();

    CourseResponse getCourseById(Long id);
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    String deleteCourse(Long id);

    List<CourseResponse> getCoursesByCategoryId(Long categoryId);
}
