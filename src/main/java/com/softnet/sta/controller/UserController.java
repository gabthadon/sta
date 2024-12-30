package com.softnet.sta.controller;

import com.softnet.sta.dto.request.InstructorRequest;
import com.softnet.sta.dto.request.LearnerRequest;
import com.softnet.sta.dto.response.*;
import com.softnet.sta.exception.InvalidRequestException;
import com.softnet.sta.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(
        name = "STA - User Controller",
        description = "Exposes REST APIs for User Controller"
)

@AllArgsConstructor
@RestController
@Slf4j
public class UserController {
    private final UserService userService;


    @Operation(
            summary = "Get loggedIn User Details REST API",
            description = "This REST API is used to Get loggedIn User Details from the Database"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "HTTP Status 200 OK")
    @GetMapping("/get/loggedIn/user")
    public ResponseEntity<ApiResponse<LoginResponse>> GetLoggedInUser() {
        try {
            LoginResponse response = userService.getLoggedInUser();
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(), "User Successfully Gotten", response));
        } catch (InvalidRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error Logging In", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to get User", null));
        }
    }


    @Operation(
            summary = "Learner Enrollment By LearnerId REST API",
            description = "This REST API is used to Enrollment Learner By LearnerId"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "HTTP Status 200 OK")
    @PutMapping("/learner/enroll")
    public ResponseEntity<ApiResponse<EnrollmentResponseWrapper>> enrollLearners(
            @RequestBody List<LearnerRequest> learnerRequests,
            @RequestParam(required = false) String discountCode) {

        EnrollmentResponseWrapper updatedLearners = userService.enrollLearners(learnerRequests, discountCode);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(200, "Learners Enrolled successfully", updatedLearners));
    }


    //Update Instructor
    @PutMapping("/update-instructor/{id}")
public ResponseEntity<ApiResponse<InstructorProfileResponse>> updateInstructorProfile(@RequestBody InstructorRequest instructorRequest, @PathVariable("id") Long id) {
    InstructorProfileResponse instructorProfileResponse = userService.updateInstructorProfile(instructorRequest, id);
    return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(200, "Instructor updated Successfully", instructorProfileResponse ));
}


//Get Instructor by User ID
    @GetMapping("/get-instructor/{id}")
    public ResponseEntity<ApiResponse<InstructorProfileResponse>> getInstructorById(@PathVariable Long id) {
        InstructorProfileResponse response = userService.getInstructorById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(200, "Instructor updated Successfully", response ));
    }


    @GetMapping("/instructor/get/all")
    public ResponseEntity<List<InstructorProfileResponse>> getAllInstructors() {
        List<InstructorProfileResponse> response = userService.getAllInstructors();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/instructor/{id}")
    public ResponseEntity<Void> deleteInstructorById(@PathVariable Long id) {
        userService.deleteInstructorById(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
