package com.softnet.sta.controller;

import com.softnet.sta.dto.request.CourseRequest;
import com.softnet.sta.dto.response.ApiResponse;
import com.softnet.sta.dto.response.CourseResponse;
import com.softnet.sta.service.interfaces.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Course Controller",
        description = "Exposes APIs to manage courses like creation, update, and deletion"
)

@RequiredArgsConstructor
@RestController
@RequestMapping("/course")
@Slf4j
public class CourseController {
    private final CourseService courseService;

    @Operation(
            summary = "Create Course REST API",
            description = "This REST API is used to create a new course"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Learner Details Fetched Successfully")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CourseResponse>> createCourse(@RequestBody CourseRequest courseRequest) {
        try {
            CourseResponse response = courseService.createCourse(courseRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(200, "Course Created Successfully", response));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(
            summary = "Update Course REST API",
            description = "This REST API is used to update an existing course by its ID"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Learner Details Fetched Successfully")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> updateCourse(@RequestBody CourseRequest courseRequest, @PathVariable("id") Long id) {
        CourseResponse courseResponse = courseService.updateCourse(courseRequest, id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "Course Updated Successfully", courseResponse));
    }

    @Operation(
            summary = "Delete Course REST API",
            description = "This REST API is used to delete an existing course by its ID"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Learner Details Fetched Successfully")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCourse(@PathVariable("id") Long id) {
        String response = courseService.deleteCourse(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "Course Deleted Successfully", response));
    }
}
