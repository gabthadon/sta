package com.softnet.sta.mapper;

import com.softnet.sta.database.entity.*;
import com.softnet.sta.dto.response.AuthorityResponse;
import com.softnet.sta.dto.response.SignUpResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Data
@Slf4j
public class UserProfileResponseMapper {
    private final ModelMapper modelMapper;

//    public SignUpResponse mapToSignUpResponse(Users user, Learner learner, OrganizationRep organizationRep) {
//        if (user == null || learner == null || organizationRep == null) {
//            return null;
//        }
//
//        SignUpResponse response = new SignUpResponse();
//        response.setFullName(user.getFirstName() + " " + user.getLastName());
//        response.setEmailAddress(user.getEmailAddress());
//        response.setPhoneNumber(user.getPhoneNumber());
//        response.setRegistrationId(user.getRegistrationId());
//        response.setEmailVerified(user.getEmailVerified());
//        // Mapping user authorities to a list of strings
//        // Check if authorities are null or empty, and map them accordingly
//        if (user.getAuthorities() != null && !user.getAuthorities().isEmpty()) {
//            List<String> authorities = user.getAuthorities()
//                    .stream()
//                    .map(authority -> authority.getAuthority().name()) // Extract authority as string
//                    .collect(Collectors.toList());
//            response.setUserAuthorities(authorities);
//        } else {
//            response.setUserAuthorities(Collections.emptyList()); // Set empty list if no authorities
//        }
//        response.setOrganization(learner.getOrganization());
//        response.setOrganization(organizationRep.getOrganization());
//        response.setJobRole(organizationRep.getJobRole());
//
//        return response;
//    }


    @Transactional
    public SignUpResponse mapToSignUpResponse(Users user, Authority authority) {
        if (user == null) {
            return null;
        }

        SignUpResponse response = new SignUpResponse();
        response.setId(user.getId());
        response.setFullName(user.getFirstName() + " " + user.getLastName());
        response.setEmailAddress(user.getEmailAddress());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setRegistrationId(user.getRegistrationId());
        response.setEmailVerified(user.getEmailVerified());
        response.setUserRole(user.getUserRole().name());

        // Access the authorities to force loading
//        List<Authority> authorities = user.getAuthorities();
//        log.info("list size:::{}", authorities.size());
//        if (authority != null && !authority.isEmpty()) {
//            List<AuthorityResponse> authorityResponses = authority.stream()
//                    .map(authority -> new AuthorityResponse(authority.getAuthority()))
//                    .collect(Collectors.toList());
//            response.setUserAuthorities(authorityResponses);
//        }
        AuthorityResponse authorityResponse = modelMapper.map(authority, AuthorityResponse.class);



        return response;
    }

    @Transactional
    public SignUpResponse mapToSignUpResponse(Users user) {
        if (user == null) {
            return null;
        }

        SignUpResponse response = new SignUpResponse();
        response.setId(user.getId());
        response.setFullName(user.getFirstName() + " " + user.getLastName());
        response.setEmailAddress(user.getEmailAddress());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setRegistrationId(user.getRegistrationId());
        response.setEmailVerified(user.getEmailVerified());
        response.setUserRole(user.getUserRole().name());

        // Access the authorities to force loading
        List<Authority> authorities = user.getAuthorities();
        if (authorities != null && !authorities.isEmpty()) {
            List<AuthorityResponse> authorityResponses = authorities.stream()
                    .map(authority -> new AuthorityResponse(authority.getAuthority()))
                    .collect(Collectors.toList());
            response.setUserAuthorities(authorityResponses);
        }


        return response;
    }

    }
