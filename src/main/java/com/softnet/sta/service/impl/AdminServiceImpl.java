package com.softnet.sta.service.impl;

import com.softnet.sta.database.entity.Users;
import com.softnet.sta.dto.response.LoginResponse;
import com.softnet.sta.dto.response.SignUpResponse;
import com.softnet.sta.enums.UserRole;
import com.softnet.sta.exception.InvalidRequestException;
import com.softnet.sta.mapper.EnrollmentResponseMapper;
import com.softnet.sta.mapper.InstructorMapper;
import com.softnet.sta.mapper.UserProfileResponseMapper;
import com.softnet.sta.repository.*;
import com.softnet.sta.service.interfaces.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final LearnerRepository learnerRepository;
    private final AdminRepository adminRepository;
    private final OrganizationRepository organizationRepository;
    private final InstructorRepository instructorRepository;
    private final AuthorityRepository authorityRepository;
    private final UserProfileResponseMapper userProfileResponseMapper;

    @Override
    public List<LoginResponse> getAllUsers() {
        List<Users> users = userRepository.findAll();
        // Map each user entity to a LoginResponse
        return users.stream().map(user -> {
            SignUpResponse signUpResponse = userProfileResponseMapper.mapToSignUpResponse(user);
//            String jwtToken = null;
            return new LoginResponse(null, signUpResponse);
        }).collect(Collectors.toList());
    }



    @Override
    public LoginResponse getUserById(Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new InvalidRequestException("User not found", 404));
        // Map the user entity to SignUpResponse
        SignUpResponse signUpResponse = userProfileResponseMapper.mapToSignUpResponse(user);
        // Return the LoginResponse
        return new LoginResponse(null, signUpResponse);
    }



    @Override
    public String deleteUser(Long id) {
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new InvalidRequestException("User not found", 404));
        // Delete the user
        userRepository.delete(user);
        return "User deleted successfully";
    }

    @Override
    public List<?> getUserByUserRole(UserRole userRole) {
//        List<Users> users = userRepository.findByUserRole(userRole);
//
//        if (users.isEmpty()) {
//            throw new InvalidRequestException("Users with the role " + userRole + " not found", 404);
//        }
//        // Map the user entities to LoginResponse
//        return users.stream().map(user -> {
//            SignUpResponse signUpResponse = userProfileResponseMapper.mapToSignUpResponse(user);
//            return new LoginResponse(null, signUpResponse);
//        }).collect(Collectors.toList());


        List<Users> users = userRepository.findByUserRole(userRole);

        if (users.isEmpty()) {
            return Collections.emptyList();  // Return an empty list if no users are found
//            return List.of();
//            throw new InvalidRequestException("Users with the role " + userRole + " not found", 404);
        }


        if (userRole == UserRole.LEARNER) {
            // Return list of EnrollmentResponse for LEARNER role
            return users.stream()
                    .map(user -> EnrollmentResponseMapper.convertToEnrollmentResponse(user.getLearner()))
                    .collect(Collectors.toList());
        } else if (userRole == UserRole.INSTRUCTOR) {
            // Return list of InstructorProfileResponse for INSTRUCTOR role
            return users.stream()
                    .map(user -> InstructorMapper.toInstructorProfileResponse(user.getInstructor()))
                    .collect(Collectors.toList());
        } else {
            // Default to list of LoginResponse for other roles
            return users.stream()
                    .map(user -> {
                        SignUpResponse signUpResponse = userProfileResponseMapper.mapToSignUpResponse(user);
                        return new LoginResponse(null, signUpResponse);
                    })
                    .collect(Collectors.toList());
        }


    }
}
