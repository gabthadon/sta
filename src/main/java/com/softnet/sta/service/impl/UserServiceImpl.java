package com.softnet.sta.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softnet.sta.config.security.JwtUtils;
import com.softnet.sta.constant.IdValues;
import com.softnet.sta.database.entity.*;
import com.softnet.sta.dto.request.InstructorRequest;
import com.softnet.sta.dto.request.LearnerRequest;
import com.softnet.sta.dto.request.LoginRequest;
import com.softnet.sta.dto.request.SignUpRequest;
import com.softnet.sta.dto.response.*;
import com.softnet.sta.enums.UserRole;
import com.softnet.sta.exception.InvalidRequestException;
import com.softnet.sta.exception.NotFoundException;
import com.softnet.sta.mapper.EnrollmentResponseMapper;
import com.softnet.sta.mapper.InstructorMapper;
import com.softnet.sta.mapper.PackageMapper;
import com.softnet.sta.mapper.UserProfileResponseMapper;
import com.softnet.sta.repository.*;
import com.softnet.sta.service.interfaces.UserService;
import com.softnet.sta.util.IDGenerator;
import com.softnet.sta.validator.UserCredValidation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static com.softnet.sta.constant.IdValues.REGISTRATION_ID;
import static com.softnet.sta.mapper.EnrollmentResponseMapper.convertToEnrollmentResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final LearnerRepository learnerRepository;
    private final AdminRepository adminRepository;
    private final OrganizationRepository organizationRepository;
    private final InstructorRepository instructorRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final UserCredValidation userCredValidation;
    private final UserProfileResponseMapper userProfileResponseMapper;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final PackageRepository packageRepository;
    private final RequirementRepository requirementRepository;
    private final RequirementServiceImpl requirementServiceImpl;
    private final DiscountRepository discountRepository;


    @Override
    public SignUpResponse signUp(SignUpRequest signUpRequest) {

        // Validate user credentials
        userCredValidation.validateFirstName(signUpRequest.getFirstName());
        userCredValidation.validateLastName(signUpRequest.getLastName());
        userCredValidation.validateEmailAddress(signUpRequest.getEmailAddress());
        userCredValidation.validatePhoneNumber(signUpRequest.getPhoneNumber());
        userCredValidation.validatePassword(signUpRequest.getPassword());
        userCredValidation.validateUserRole(signUpRequest.getRole());

        // entity initialization
        Authority authority = new Authority();
        Users user = new Users();
        Learner learner = new Learner();
        OrganizationRep organizationRep = new OrganizationRep();
        Instructor instructor = new Instructor();
        Admin admin = new Admin();
        // save user unique credentials to user table
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setEmailAddress(signUpRequest.getEmailAddress());
        user.setPhoneNumber(signUpRequest.getPhoneNumber());
        user.setUserRole(signUpRequest.getRole());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRegistrationId(IDGenerator.generateID(REGISTRATION_ID));
        user.setEmailVerified(false);
        Users savedUser = userRepository.save(user);

        // Assign the role to Authority entity and link it to the user
        authority.setAuthority(signUpRequest.getRole()); // Set the user role (authority)
        authority.setUsers(savedUser); // Link the authority to the user
        Authority savedAuthority = authorityRepository.save(authority); // Save the authority entity to the database

        if (signUpRequest.getRole().equals(UserRole.LEARNER)) {
            // save learner details
            learner.setUsers(savedUser);
            learner.setOrganization(signUpRequest.getOrganization());
            learner.setOrganizationRepId("STA-SS");
            learner.setIsEnrolled(false);
            learnerRepository.save(learner);
        }
        if (signUpRequest.getRole().equals(UserRole.ORGANIZATION_REP)) {
            // save organization representative details
            organizationRep.setUsers(savedUser);
            organizationRep.setOrganization(signUpRequest.getOrganization());
            organizationRep.setJobRole(signUpRequest.getJobRole());
            organizationRepository.save(organizationRep);
        }
        if (signUpRequest.getRole().equals(UserRole.ADMIN)) {
            // save admin details
            admin.setUsers(savedUser);
            adminRepository.save(admin);
        }
        if (signUpRequest.getRole().equals(UserRole.INSTRUCTOR)) {
            // save instructor details
            instructor.setUsers(savedUser);
            instructorRepository.save(instructor);
        }

        // map the saved user details to SignUpResponse
        return userProfileResponseMapper.mapToSignUpResponse(savedUser, savedAuthority);

    }


    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        Authentication authentication;
        try {
            // Authenticate the user using email and password
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmailAddress(), loginRequest.getPassword())
            );
        } catch (AuthenticationException exception) {
            throw new InvalidRequestException("Bad credentials", 401); // Unauthorized status
        }
        // Set the authentication context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Get the authenticated user details
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // Generate JWT token including the user's authorities (roles)
        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);
        // Retrieve the user entity from the database using the email
        Users user = userRepository.findByEmailAddress(userDetails.getUsername());
        if (user == null) {
            throw new InvalidRequestException("User not found", 404); // User not found
        }
        // Map the user and related details to SignUpResponse
        SignUpResponse signUpResponse = userProfileResponseMapper.mapToSignUpResponse(user);
        // Return the LoginResponse with authToken and SignUpResponse
        return new LoginResponse(jwtToken, signUpResponse);
    }

    @Override
    public LoginResponse getLoggedInUser() {
        // Get the logged-in user's username (email) from the SecurityContext
        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        // Retrieve the user entity from the database using the email
        Users user = userRepository.findByEmailAddress(loggedInUsername);
        if (user == null) {
            throw new InvalidRequestException("User not found", 404);
        }
        // Map the user entity to SignUpResponse
        SignUpResponse signUpResponse = userProfileResponseMapper.mapToSignUpResponse(user);
        // Return the LoginResponse without generating a new JWT token
        return new LoginResponse(null, signUpResponse);
    }


    @Override
    @Transactional
    public EnrollmentResponse getLearnerByUserId(Long userId) {
        // Find the user by userId
        Optional<Users> user = userRepository.findById(userId);

        if (user.isEmpty() || user.get().getUserRole() != UserRole.LEARNER) {
            throw new NotFoundException("User not found with id: " + userId, 404);
        }

        Users userData = user.get();

        // Find the learner by the user

        Optional<Learner> learner = learnerRepository.findByUsers(userData);

        if (learner.isEmpty()) {
            throw new NotFoundException("Learner not found with id: " + userId, 404);
        }

        Learner ln = learner.get();
        ln.setUsers(userData);

        // Convert Learner to EnrollmentResponse
        return convertToEnrollmentResponse(ln);
    }


    @Override
    @Transactional
    public EnrollmentResponse getLearnerByRegistrationId(String registrationId) {

        Optional<Users> user = userRepository.findByRegistrationId(registrationId);


        if (user.isEmpty() || user.get().getUserRole() != UserRole.LEARNER) {
            throw new NotFoundException("User not found with id: " + registrationId, 404);
        }

        Users userData = user.get();


        Optional<Learner> learner = learnerRepository.findByUsers(userData);

        Learner learnerData = learner.get();


        return convertToEnrollmentResponse(learnerData);
    }

    @Transactional
    @Override
    public EnrollmentResponseWrapper enrollLearners(List<LearnerRequest> learnerRequests, String discountCode) {

        String regId = IdValues.ORGANIZATIONID;
        String generatedId = IDGenerator.generateID(regId);
        Integer count = learnerRequests.size();
        List<EnrollmentResponse> updatedLearners = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;



        // Loop through learners and process each learner's enrollment
        for (LearnerRequest learnerRequest : learnerRequests) {
            Long userId = learnerRequest.getUserId();

         Optional<Users> users =  userRepository.findById(userId);
         if (users.isEmpty() || users.get().getUserRole() != UserRole.LEARNER) {
             throw new NotFoundException("Learner not found with id: " + userId, 404);
         }


            // Find learner by ID
            Learner learner = learnerRepository.findById(users.get().getLearner().getId()).orElse(null);


            // Update learner details from the request
            learner.setCountry(learnerRequest.getCountry());
            learner.setState(learnerRequest.getState());
            learner.setAddress(learnerRequest.getAddress());
            learner.setCity(learnerRequest.getCity());
            learner.setMedFitnessDocUrl(learnerRequest.getMedFitnessDocUrl());
            learner.setAvailability(learnerRequest.getAvailability());
            learner.setIdCardUrl(learnerRequest.getIdCardUrl());
            learner.setIsEnrolled(true);
            learner.setEnrollmentId(generatedId);
            learner.setProfileImageUrl(learner.getProfileImageUrl());
            learner.setCohortName(learnerRequest.getCohortName());
            learner.setPassportPhotoUrl(learnerRequest.getPassportPhotoUrl());


            // Get user
            /*
            Optional<Users> user = userRepository.findById(learnerId);
            user.ifPresent(learner::setUsers);
*/

            // Get Package and set if provided
            if (learnerRequest.getPackageId() != null) {
                Packages learnerPackage = packageRepository.findById(learnerRequest.getPackageId())
                        .orElseThrow(() -> new ResourceNotFoundException("Package not found with id: " + learnerRequest.getPackageId()));

                learner.setPackages(learnerPackage);

                // Use PackageMapper to convert to PackageResponse
                PackageResponse packageResponse = PackageMapper.topackageResponse(learnerPackage);

                // Add package amount to total
                total = total.add(learnerPackage.getAmount());

                // Save the updated learner
                learner = learnerRepository.save(learner);

                // Convert the updated learner to EnrollmentResponse and include PackageResponse
                EnrollmentResponse enrollmentResponse = convertToEnrollmentResponse(learner);
                enrollmentResponse.setPackageResponse(packageResponse);  // Set the mapped PackageResponse

                updatedLearners.add(enrollmentResponse);
            }

            // Always check for unmet requirements and add the amount
            if (!learnerRequest.getIsRequirementMet()) {
                if (learnerRequest.getPackageId() != null) {
                    Packages learnerPackage = learner.getPackages();
                    List<Requirement> requirements = learnerPackage.getRequirement();

                    // Set unmet requirements to false in the response
                    learnerRequest.setIsRequirementMet(false);

                    for (Requirement requirement : requirements) {
                        BigDecimal requirementAmount = requirement.getAmount();
                        total = total.add(requirementAmount);
                    }
                }
            } else {
                learnerRequest.setIsRequirementMet(true);
            }
        }

        // Check for discount outside the learner loop
        if (discountCode != null) {
            List<Object[]> discount = discountRepository.applyDiscount(discountCode);

            // If the database query returns an empty result, throw an error
            if (discount.isEmpty()) {
                throw new InvalidRequestException("Discount code not found", 407);
            }

            // Assuming only one discount should be applied, fetch the first one
            Object[] discountRecord = discount.get(0);
            Integer discountPercentage = (Integer) discountRecord[2];

            // Calculate discount once, based on the total
            BigDecimal discountAmount = total.multiply(BigDecimal.valueOf(discountPercentage)).divide(new BigDecimal("100"));
            total = total.subtract(discountAmount);



            // Use the provided discount code to get the discount object
            Discount disc = discountRepository.findByCode(discountCode);

            // Update discount usage count
            disc.setUsageCount(count.longValue() + disc.getUsageCount());

            // Update the Discount Table
            discountRepository.save(disc);
        }

        // Return total amount and updated learners
        return new EnrollmentResponseWrapper(count, total, generatedId, updatedLearners);
    }



    @Override
    public List<EnrollmentResponse> getLearnerBycorhotName(String cohortName) {
        // Fetch learners by cohort name from the repository
        List<Learner> learners = learnerRepository.findByCohortName(cohortName);

        // Convert the list of Learner entities to EnrollmentResponse DTOs
        List<EnrollmentResponse> learnerResponse = learners.stream()
                .map(EnrollmentResponseMapper::convertToEnrollmentResponse)
                .collect(Collectors.toList());

        // Return the list of EnrollmentResponse
        return learnerResponse;
    }

    @Override
    public List<EnrollmentResponse> getLearnerByEnrollmentId(String enrollmentId) {

     List<Learner>  learners =  learnerRepository.findByEnrollmentId(enrollmentId);

        List<EnrollmentResponse> learnerResponse = learners.stream()
                .map(EnrollmentResponseMapper::convertToEnrollmentResponse)
                .collect(Collectors.toList());

        // Return the list of EnrollmentResponse
        return learnerResponse;
    }


    @Override
    public String forgotPassword(String emailAddress) {
        // Find user by email
        Users user = userRepository.findByEmailAddress(emailAddress);
        if (user == null) {
            throw new InvalidRequestException("User not found", 404); // User not found
        }

        // Generate a random OTP (6-digit number)
        String otp = String.valueOf(new Random().nextInt(999999 - 100000) + 100000);

        // Save the OTP to the user's entity
        user.setOtp(otp);
        user.setOtpExpiryDate(LocalDateTime.now().plusMinutes(10)); // OTP is valid for 10 minutes
        userRepository.save(user);

        // Return the OTP for now (this would not be exposed in a real app, for security reasons)
        return otp;
    }




    @Override
    public void resetPassword(String emailAddress, String otp, String newPassword) {
        // Find user by email
        Users user = userRepository.findByEmailAddress(emailAddress);
        if (user == null) {
            throw new InvalidRequestException("User not found", 404); // User not found
        }

        // Validate the OTP and expiry date
        if (!otp.equals(user.getOtp()) || user.getOtpExpiryDate().isBefore(LocalDateTime.now())) {
            throw new InvalidRequestException("Invalid or expired OTP", 400); // Invalid OTP
        }

        // Update the user's password (you should encode the password)
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setOtp(null);  // Clear the OTP
        user.setOtpExpiryDate(null);  // Clear the OTP expiry date
        userRepository.save(user);
    }

    @Override
    public InstructorProfileResponse updateInstructorProfile(InstructorRequest instructorRequest, Long id) {
        // Fetch the user by ID
        Optional<Users> instructorOptional = userRepository.findById(id);

        // Handle the case where the instructor is not found
        if (!instructorOptional.isPresent()) {
            throw new InvalidRequestException("Instructor not found with id " + id, 404);
        }

        Users instructorData = instructorOptional.get();

        // Check if the user is actually an instructor
        if (instructorData.getUserRole() != UserRole.INSTRUCTOR) {
            throw new InvalidRequestException("User is not an instructor", 404);
        }


            instructorData.setFirstName(instructorRequest.getFirstName());


            instructorData.setLastName(instructorRequest.getLastName());


            instructorData.setPhoneNumber(instructorRequest.getPhoneNumber());


        // Check if the instructor entity is present
        Instructor instructorEntity = instructorData.getInstructor();
        if (instructorEntity == null) {
            throw new InvalidRequestException("Instructor details not found for user with id " + id, 404);
        }


            instructorEntity.setBio(instructorRequest.getBio());

            instructorEntity.setProfileImageUrl(instructorRequest.getProfileImageUrl());


        // Save the updated user and instructor entity
        userRepository.save(instructorData);

        // Return the updated profile response
        return InstructorMapper.toInstructorProfileResponse(instructorEntity);
    }




    @Override
    public InstructorProfileResponse getInstructorById(Long id) {
        // Fetch the user by ID
        Optional<Users> instructorOptional = userRepository.findById(id);

        // Handle the case where the instructor is not found
        if (!instructorOptional.isPresent()) {
            throw new InvalidRequestException("Instructor not found with id " + id, 404);
        }

        Users instructorData = instructorOptional.get();

        // Check if the user is actually an instructor
        if (instructorData.getUserRole() != UserRole.INSTRUCTOR) {
            throw new InvalidRequestException("User is not an instructor", 404);
        }

        // Check if the instructor entity is present
        Instructor instructorEntity = instructorData.getInstructor();
        if (instructorEntity == null) {
            throw new InvalidRequestException("Instructor details not found for user with id " + id, 404);
        }

        // Return the instructor profile response
        return InstructorMapper.toInstructorProfileResponse(instructorEntity);
    }


    @Override
    public void deleteInstructorById(Long id) {
        // Fetch the user by ID
        Optional<Users> instructorOptional = userRepository.findById(id);

        // Handle the case where the instructor is not found
        if (!instructorOptional.isPresent()) {
            throw new InvalidRequestException("Instructor not found with id " + id, 404);
        }

        Users instructorData = instructorOptional.get();

        // Check if the user is actually an instructor
        if (instructorData.getUserRole() != UserRole.INSTRUCTOR) {
            throw new InvalidRequestException("User is not an instructor", 404);
        }

        // Delete the instructor
        userRepository.delete(instructorData);
    }



    @Override
    public List<InstructorProfileResponse> getAllInstructors() {
        List<Users> instructors = userRepository.findAllByUserRole(UserRole.INSTRUCTOR);
        return instructors.stream()
                .map(user -> {
                    Instructor instructorEntity = user.getInstructor();
                    return InstructorMapper.toInstructorProfileResponse(instructorEntity);
                })
                .collect(Collectors.toList());
    }


}
