package com.softnet.sta.service.interfaces;

import com.softnet.sta.dto.request.InstructorRequest;
import com.softnet.sta.dto.request.LearnerRequest;
import com.softnet.sta.dto.request.LoginRequest;
import com.softnet.sta.dto.request.SignUpRequest;
import com.softnet.sta.dto.response.*;

import java.util.List;


public interface UserService {
    SignUpResponse signUp(SignUpRequest signUpRequest);

    LoginResponse login(LoginRequest loginRequest);

    LoginResponse getLoggedInUser();

   
    EnrollmentResponse getLearnerByUserId(Long userId);

    EnrollmentResponse getLearnerByRegistrationId(String registrationId);



    EnrollmentResponseWrapper enrollLearners(List<LearnerRequest> learnerRequests, String discountCode);

    List<EnrollmentResponse> getLearnerBycorhotName(String corhotName);

    List<EnrollmentResponse> getLearnerByEnrollmentId(String enrollmentId);

    String forgotPassword(String email);

    void resetPassword(String email, String otp, String newPassword);


    InstructorProfileResponse updateInstructorProfile(InstructorRequest instructorRequest, Long id);

    InstructorProfileResponse getInstructorById(Long id);

    void deleteInstructorById(Long id);

    List<InstructorProfileResponse> getAllInstructors();
}
