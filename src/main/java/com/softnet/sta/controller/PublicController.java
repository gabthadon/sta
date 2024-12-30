package com.softnet.sta.controller;

import com.softnet.sta.dto.request.LoginRequest;
import com.softnet.sta.dto.request.SignUpRequest;
import com.softnet.sta.dto.response.*;
import com.softnet.sta.exception.InvalidRequestException;
import com.softnet.sta.service.impl.FileUploadService;
import com.softnet.sta.service.interfaces.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Tag(
        name = "Public Controller",
        description = "Exposes public REST APIs for user registration, login, file uploads, categories, courses, packages, and learner details"
)

@AllArgsConstructor
@RestController
@Slf4j
@RequestMapping("/public")
public class PublicController {
    private final UserService userService;
    private final FileUploadService fileUploadService;
    private final CategoryService categoryService;
    private final CourseService courseService;
    private final PackageService packageService;
    private final RequirementService requirementService;
    private final HeroSliderService heroSliderService;
    private final CohortService cohortService;
    private final LoginSlideService loginSlideService;


    @Operation(
            summary = "User Sign Up REST API",
            description = "This REST API is used to Sign Up a New User"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "User Signed Up Successfully")
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignUpResponse>> UserSignUp(@RequestBody SignUpRequest signUpRequest) {
        try {
            SignUpResponse response = userService.signUp(signUpRequest);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(), "You Have Signed Up successfully", response));
        } catch (InvalidRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error Singing Up", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to Sign Up New User", null));
        }
    }


    @Operation(
            summary = "User Login REST API",
            description = "This REST API is used to Log In an Existing User"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User Logged In Successfully")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> UserLogin(@RequestBody @Valid LoginRequest loginRequest) {
        try {
            LoginResponse response = userService.login(loginRequest);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(), "You Have Logged In successfully", response));
        } catch (InvalidRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error Logging In", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to Login User", null));
        }
    }

    @Operation(
            summary = "File Upload REST API",
            description = "This REST API is used to Upload an Image or File"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "File Uploaded Successfully")
    @PostMapping("/image/upload")
    public ResponseEntity<ApiResponse> uploadFile(@RequestPart("file") MultipartFile file) {
        log.info("File::::::" + file.getOriginalFilename());
        ApiResponse<String> response = fileUploadService.uploadFile(file);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }
//

    @Operation(
            summary = "Get All Categories REST API",
            description = "This REST API is used to Retrieve All Categories"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Categories Retrieved Successfully")
    @GetMapping("/category/all")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories() {
        List<CategoryResponse> categoryResponses = categoryService.getAllCategories();
        return ResponseEntity.ok(new ApiResponse<>(200, "Categories Retrieved Successfully", categoryResponses));
    }


    @Operation(
            summary = "Get Category By Id REST API",
            description = "This REST API is used to Retrieve a Category by Id"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Category Retrieved Successfully")
    @GetMapping("/category/get/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(@PathVariable Long id) {
        CategoryResponse categoryResponse = categoryService.getCategoryById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Category Retrieved Successfully", categoryResponse));
    }


    @Operation(
            summary = "Get All Courses REST API",
            description = "This REST API is used to Retrieve All Courses"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Courses Retrieved Successfully")
    @GetMapping("/course/get-all")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getAllCourses() {
        List<CourseResponse> response = courseService.getAllCourses();

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(200, "Course Retreived Successfully", response));
    }

    @Operation(
            summary = "Get Course By Id REST API",
            description = "This REST API is used to Retrieve a Course by Id"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Course Retrieved Successfully")
    @GetMapping("/course/get/by/id/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> getCourseById(@PathVariable("id") Long id) {
        CourseResponse response =  courseService.getCourseById(id);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(200, "Course Retreived Successfully", response));

    }


    // Get Package by ID
    @Operation(
            summary = "Get Package By Id REST API",
            description = "This REST API is used to Retrieve a Package by Id"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Package Retrieved Successfully")
    @GetMapping("/package/{id}")
    public ResponseEntity<ApiResponse<PackageResponse>> getPackageById(@PathVariable Long id) {
        PackageResponse response = packageService.getPackageById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "Package retrieved successfully", response));
    }

    @Operation(
            summary = "Get All Packages REST API",
            description = "This REST API is used to Retrieve All Packages"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Packages Retrieved Successfully")
    @GetMapping("/package/all")
    public ResponseEntity<ApiResponse<List<PackageResponse>>> getAllPackages() {
        List<PackageResponse> response = packageService.getAllPackages();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "Packages retrieved successfully", response));
    }

    @Operation(
            summary = "Get Courses By Category Id REST API",
            description = "This REST API is used to Retrieve Courses by Category Id"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Courses Retrieved Successfully")
    @GetMapping("/get/courses/by/categoryid/{categoryId}")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getCoursesByCategoryId(@PathVariable Long categoryId) {
        List<CourseResponse> response = courseService.getCoursesByCategoryId(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(200, "Courses Retrieved Successfully", response));
    }

    @Operation(
            summary = "Get Packages By Category Id REST API",
            description = "This REST API is used to Retrieve Packages by Category Id"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Packages Retrieved Successfully")
    @GetMapping("/get-package/by/{categoryId}")
    public ResponseEntity<ApiResponse<List<PackageResponse>>> getPackagesByCategoryId(@PathVariable Long categoryId) {
        List<PackageResponse> response = packageService.getPackagesByCategoryId(categoryId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Packages Retrieved Successfully", response));
    }

    @Operation(
            summary = "Get Learner By User Id REST API",
            description = "This REST API is used to Retrieve a Learner's Enrollment Details by User Id"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Learner Details Fetched Successfully")
    @GetMapping("/learner/user/{userId}")
    public ResponseEntity<ApiResponse<EnrollmentResponse>> getLearnerByUserId(@PathVariable("userId") Long userId) {
        EnrollmentResponse learnerResponse = userService.getLearnerByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(200, "Learner details fetched successfully", learnerResponse));
    }

    @Operation(
            summary = "Get Learner By Registration Id REST API",
            description = "This REST API is used to Retrieve a Learner's Enrollment Details by Registration Id"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Learner Details Fetched Successfully")
    @GetMapping("/learner/registration/{registrationId}")
    public ResponseEntity<ApiResponse<EnrollmentResponse>> getLearnerByRegistrationId(@PathVariable("registrationId") String registrationId) {
        EnrollmentResponse learnerResponse = userService.getLearnerByRegistrationId(registrationId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(200, "Learner details fetched successfully", learnerResponse));
    }


    @Operation(
            summary = "Get Learners By Enrollment Id REST API",
            description = "This REST API is used to Retrieve a Learner's Enrollment Details by Enrollment Id"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Learner Details Fetched Successfully")
    @GetMapping("/learner/enrollment/{enrollmentId}")
    public ResponseEntity<ApiResponse<List<EnrollmentResponse>>> getLearnerByEnrollmentId(@PathVariable("enrollmentId") String enrollmentId) {
       List <EnrollmentResponse> learnerResponse = userService.getLearnerByEnrollmentId(enrollmentId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(200, "Learner details fetched successfully", learnerResponse));
    }





    @Operation(
            summary = "Get Learner By corhot name REST API",
            description = "This REST API is used to Retrieve a Learner's  by Corhort Name"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Learner Details Fetched Successfully")
    @GetMapping("/learner/corhotname/{corhotName}")
    public ResponseEntity<ApiResponse<List<EnrollmentResponse>>> getLearnerByCorhotNamt(@PathVariable("corhotName") String corhotName) {
       List<EnrollmentResponse> learnerResponse = userService.getLearnerBycorhotName(corhotName);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(200, "Learner details fetched successfully", learnerResponse));
    }




    @Operation(
            summary = "Get All Hero Sliders REST API",
            description = "This REST API is used to All HeroSliders"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Hero Sliders gotten Successfully")
    @GetMapping("/get/all/heroSlides")
    public ResponseEntity<ApiResponse<List<HeroSliderResponse>>> getAllHeroSlider() {
        try {
            List<HeroSliderResponse> response = heroSliderService.getAllHeroSlider();
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(), "List of Hero Slider Gotten successfully", response));
        } catch (Exception e) {
            log.error("Error getting List of Hero Slider", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to get List of Hero Slider", null));
        }
    }

    @Operation(
            summary = "Get Hero Sliders By Id REST API",
            description = "This REST API is used to get HeroSlider by Id"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "HeroSlider Gotten Successfully")
    @GetMapping("/get/heroSlide/by/id/{id}")
    public ResponseEntity<ApiResponse<HeroSliderResponse>> getHeroSliderById(@PathVariable Long id) {
        try {
            HeroSliderResponse response = heroSliderService.getHeroSliderById(id);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(), "Hero Slider Successfully Gotten", response));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error getting Hero Slider", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to get Hero Slider", null));
        }
    }

    @Operation(
            summary = "Get Requirement By PackageId REST API",
            description = "This REST API is used to get Requirement By PackageId"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Requirement gotten Successfully")
    //Get Requirement By PackageId
    @GetMapping("/requirement/package/{packageId}")
    public ResponseEntity<ApiResponse<List<RequirementResponse>>> getRequirementsByPackageId(@PathVariable("packageId") Long packageId) {
        List<RequirementResponse> requirements = requirementService.getRequirementsByPackageId(packageId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(200, "Requirements fetched successfully", requirements));
    }

    @Operation(
            summary = "Get Requirement By Id REST API",
            description = "This REST API is used to get Requirement By Id"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Requirement gotten Successfully")
    @GetMapping("/requirement/{requirementId}")
    public ResponseEntity<ApiResponse<RequirementResponse>> getRequirementById(@PathVariable("requirementId") Long requirementId) {
        RequirementResponse requirementResponse = requirementService.getRequirementById(requirementId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(200, "Requirement retrieved successfully", requirementResponse));
    }


    @Operation(
            summary = "Get All Active Cohort REST API",
            description = "This REST API is used to All Active Cohort"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Active Cohorts gotten Successfully")
    @GetMapping("/get/all/active/cohort")
    public ResponseEntity<ApiResponse<List<CohortResponse>>> getAllActiveCohort() {
        try {
            List<CohortResponse> response = cohortService.getAllActiveCohort();
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(), "List of Active Cohort Gotten successfully", response));
        } catch (Exception e) {
            log.error("Error getting List of Active Cohort", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to get List of Active Cohort", null));
        }
    }

    @Operation(
            summary = "Get Cohort By Id REST API",
            description = "This REST API is used to get Cohort by Id"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Cohort Gotten Successfully")
    @GetMapping("/get/cohort/by/id/{id}")
    public ResponseEntity<ApiResponse<CohortResponse>> getCohortById(@PathVariable Long id) {
        try {
            CohortResponse response = cohortService.getCohortById(id);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(), "Cohort Successfully Gotten", response));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error getting Cohort", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to get Cohort", null));
        }
    }


    @Operation(
            summary = "Forgot Password REST API",
            description = "This REST API is used for Forgot Password"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "OTP Generated Successfully")
    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<String>> forgotPassword(@RequestParam("email") String email) {
        try {
            // Generate OTP and return it (for demo purposes, but don't do this in production)
            String otp = userService.forgotPassword(email);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "OTP generated successfully. Use this OTP to reset your password.", otp));
        } catch (InvalidRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error while processing forgot password request", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to process forgot password request", null));
        }

    }



    @Operation(
            summary = "Reset Password REST API",
            description = "This REST API is used to Reset Password"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Password Reset Successfully")
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<String>> resetPassword(
            @RequestParam("email") String email,
            @RequestParam("otp") String otp,
            @RequestParam("newPassword") String newPassword) {
        try {
            userService.resetPassword(email, otp, newPassword);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Password reset successfully", null));
        } catch (InvalidRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error while resetting password", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to reset password", null));
        }
    }


    @Operation(
            summary = "Get All Login Slider REST API",
            description = "This REST API is used to get all Login Slider"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Login Slider Gotten Successfully")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<HeroSliderResponse>>> getAllLoginSlides() {
        List<HeroSliderResponse> response = loginSlideService.getAllLoginSlides();
        return ResponseEntity.ok(new ApiResponse<>(200, "List of Login Slider Fetched Successfully", response));
    }


    @Operation(
            summary = "Get Login Slider by Id REST API",
            description = "This REST API is used to get a Login Slider by Id"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Login Slider Gotten Successfully")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HeroSliderResponse>> getLoginSlideById(@PathVariable Long id) {
        HeroSliderResponse response = loginSlideService.getLoginSlideById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Login Slider Fetched Successfully", response));
    }
}
