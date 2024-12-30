package com.softnet.sta.service.impl;

import com.softnet.sta.database.entity.Authority;
import com.softnet.sta.database.entity.GroupLearnersDefaultPwd;
import com.softnet.sta.database.entity.Learner;
import com.softnet.sta.database.entity.Users;
import com.softnet.sta.dto.response.LoginResponse;
import com.softnet.sta.dto.response.SignUpResponse;
import com.softnet.sta.enums.UserRole;
import com.softnet.sta.exception.InvalidRequestException;
import com.softnet.sta.mapper.UserProfileResponseMapper;
import com.softnet.sta.repository.AuthorityRepository;
import com.softnet.sta.repository.GLDefaultPwdRepository;
import com.softnet.sta.repository.LearnerRepository;
import com.softnet.sta.repository.UserRepository;
import com.softnet.sta.service.interfaces.OrgRepService;
import com.softnet.sta.util.IDGenerator;
import com.softnet.sta.validator.UserCredValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.softnet.sta.constant.IdValues.REGISTRATION_ID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrgRepServiceImpl implements OrgRepService {

    private final UserRepository userRepository;
    private final LearnerRepository learnerRepository;
    private final AuthorityRepository authorityRepository;
    private final GLDefaultPwdRepository glDefaultPwdRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserCredValidation userCredValidation;
    private final UserProfileResponseMapper userProfileResponseMapper;

    @Override
    public List<SignUpResponse> uploadGroupLearners(MultipartFile file) {
        List<SignUpResponse> responses = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean isFirstLine = true; // to skip the CSV header

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // skip the header line
                    continue;
                }
                String[] fields = line.split(","); // Assuming CSV fields are comma-separated
                if (fields.length != 6) { // Expecting 7 fields: firstName, lastName, emailAddress, phoneNumber, password, organization, organizationRepId
                    throw new InvalidRequestException("Invalid CSV format", 400);
                }
                // Extract and validate fields
                String firstName = fields[0].trim();
                String lastName = fields[1].trim();
                String emailAddress = fields[2].trim();
                String phoneNumber = fields[3].trim();
//                String password = fields[4].trim();
                String organization = fields[4].trim();
                String organizationRepId = fields[5].trim();

                String glDefaultPwd = generateGroupLearnerDefaultPassword(firstName, phoneNumber);

                userCredValidation.validateFirstName(firstName);
                userCredValidation.validateLastName(lastName);
                userCredValidation.validateEmailAddress(emailAddress);
                userCredValidation.validatePhoneNumber(phoneNumber);
                userCredValidation.validatePassword(glDefaultPwd);

                // Save default password to the database
                GroupLearnersDefaultPwd defaultPwd = new GroupLearnersDefaultPwd();
                defaultPwd.setDefaultPassword(glDefaultPwd);
                defaultPwd.setEmailAddress(emailAddress);
                glDefaultPwdRepository.save(defaultPwd);


                // Create a new user and set learner-specific details
                Users user = new Users();
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmailAddress(emailAddress);
                user.setPhoneNumber(phoneNumber);
                user.setUserRole(UserRole.LEARNER); // Ensure the user role is LEARNER
                user.setPassword(passwordEncoder.encode(glDefaultPwd));
                user.setRegistrationId(IDGenerator.generateID(REGISTRATION_ID));
                user.setEmailVerified(false);
                Users savedUser = userRepository.save(user);

                // Assign LEARNER role to the authority
                Authority authority = new Authority();
                authority.setAuthority(UserRole.LEARNER);
                authority.setUsers(savedUser);
                authorityRepository.save(authority);

                // Save learner-specific details
                Learner learner = new Learner();
                learner.setUsers(savedUser);
                learner.setOrganization(organization);
                learner.setOrganizationRepId(organizationRepId);
                learnerRepository.save(learner);

                // Map saved user details to SignUpResponse
                SignUpResponse signUpResponse = userProfileResponseMapper.mapToSignUpResponse(savedUser, authority);
                responses.add(signUpResponse);
            }
        } catch (IOException e) {
            throw new InvalidRequestException("Failed to process CSV file", 500);
        }
        return responses;
    }

    @Override
    public List<LoginResponse> getUserByOrgRepId(String orgRepId) {
        List<Learner> learners = learnerRepository.findByOrganizationRepId(orgRepId);

        if (learners.isEmpty()) {
            throw new InvalidRequestException("Learners with the OrganizationRepId: " + orgRepId + " not found", 404);
        }
        // Map the user entities to LoginResponse
        return learners.stream().map(user -> {
            SignUpResponse signUpResponse = userProfileResponseMapper.mapToSignUpResponse(user.getUsers());
            return new LoginResponse(null, signUpResponse);
        }).collect(Collectors.toList());
    }



    private String generateGroupLearnerDefaultPassword(String firstName, String phoneNumber) {
        // Ensure the name and number have enough characters to generate a valid password
        if (firstName == null || phoneNumber == null || firstName.length() < 2 || phoneNumber.length() < 2) {
            throw new IllegalArgumentException("Invalid input for generating default password");
        }

        Random random = new Random();

        // Pick 2 random characters from the firstName: One lowercase, one uppercase
        char lowerCaseLetter = Character.toLowerCase(firstName.charAt(random.nextInt(firstName.length())));
        char upperCaseLetter = Character.toUpperCase(firstName.charAt(random.nextInt(firstName.length())));

        // Pick 2 random digits from the phoneNumber
        char digit1 = phoneNumber.charAt(random.nextInt(phoneNumber.length()));
        char digit2 = phoneNumber.charAt(random.nextInt(phoneNumber.length()));

        // Define possible special characters
        String specialCharacters = "@#$%^&+=!";
        char specialCharacter = specialCharacters.charAt(random.nextInt(specialCharacters.length()));

        // Ensure password is at least 8 characters
        StringBuilder defaultPassword = new StringBuilder(String.valueOf(upperCaseLetter) + lowerCaseLetter + digit1 + digit2 + specialCharacter);

        // If password length is less than 8, pad it with random characters
        while (defaultPassword.length() < 8) {
            char randomChar = (char) ('a' + random.nextInt(26)); // Add random lowercase letters
            defaultPassword.append(randomChar);
        }

        return defaultPassword.toString();
    }
}
