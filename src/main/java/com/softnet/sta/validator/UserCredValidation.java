package com.softnet.sta.validator;

import com.softnet.sta.enums.UserRole;
import com.softnet.sta.exception.InvalidRequestException;
import com.softnet.sta.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCredValidation {
    private final UserRepository userRepository;


    // FirstName validation
    public void validateFirstName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new InvalidRequestException("First name must be provided.", 400);
        }
    }

    // LastName validation
    public void validateLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new InvalidRequestException("Last name must be provided.", 400);
        }
    }

    // EmailAddress validation
    public void validateEmailAddress(String emailAddress) {
        if (emailAddress == null || emailAddress.trim().isEmpty()) {
            throw new InvalidRequestException("Email address must be provided.", 400);
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!emailAddress.matches(emailRegex)) {
            throw new InvalidRequestException("Invalid Email Address Format.", 400);
        }

        if (userRepository.existsByEmailAddress(emailAddress)) {
            throw new InvalidRequestException("Email address is already used.", 400);
        }
    }

    // UserRole validation
    public void validateUserRole(UserRole userRole) {
        if (userRole == null) {
            throw new InvalidRequestException("User role must be provided.", 400);
        }
    }

    // PhoneNumber validation and uniqueness check
    public void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new InvalidRequestException("Phone number must be provided.", 400);
        }
        // Ensure the phone number starts with 0 and is 11 digits number
        if (!phoneNumber.matches("^0\\d{10}$")) {
            throw new InvalidRequestException("Phone number must be 11 digits number and start with 0.", 400);
        }
        // Ensure the phone number is unique
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new InvalidRequestException("Phone number is already used.", 400);
        }
    }

    public void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new InvalidRequestException("Password must be provided.", 400);
        }
        // Ensure the password contains at least one digit, one lowercase letter, one uppercase letter, and one special symbol
        if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$")) {
            throw new InvalidRequestException(
                    "Password must be at least 8 characters long, contain at least one digit, one lowercase letter, one uppercase letter, and one special symbol (@#$%^&+=!).",
                    400
            );
        }
    }
}
