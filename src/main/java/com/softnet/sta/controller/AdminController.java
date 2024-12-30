package com.softnet.sta.controller;

import com.softnet.sta.dto.response.ApiResponse;
import com.softnet.sta.dto.response.LoginResponse;
import com.softnet.sta.enums.UserRole;
import com.softnet.sta.exception.InvalidRequestException;
import com.softnet.sta.service.interfaces.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "STA - Admin Controller",
        description = "Exposes REST APIs for Admin Controller"
)

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/admin")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class AdminController {
    private final AdminService adminService;


    @Operation(
            summary = "Get All Users REST API",
            description = "This REST API is used to Get All Users In the Application"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "HTTP Status 200 OK")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "HTTP Status 400 BAD REQUEST")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "HTTP Status 500 INTERNAL SERVER ERROR")
    @GetMapping("/get/all/users")
    public ResponseEntity<ApiResponse<List<LoginResponse>>> GetAllUsers() {
        try {
            List<LoginResponse> response = adminService.getAllUsers();
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(), "List of all Users Successfully Gotten", response));
        } catch (Exception e) {
            log.error("Error Logging In", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to get List of all Users", null));
        }
    }


    @Operation(
            summary = "Get User By UserId REST API",
            description = "This REST API is used to Get User By UserId"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "HTTP Status 200 OK")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "HTTP Status 400 BAD REQUEST")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "HTTP Status 500 INTERNAL SERVER ERROR")
    @GetMapping("/get/user/by/id/{id}")
    public ResponseEntity<ApiResponse<LoginResponse>> GetUserById(@PathVariable Long id) {
        try {
            LoginResponse response = adminService.getUserById(id);
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
            summary = "Get All Users By userRole REST API",
            description = "This REST API is used to Get All Users By userRole"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "HTTP Status 200 OK")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "HTTP Status 400 BAD REQUEST")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "HTTP Status 500 INTERNAL SERVER ERROR")
    @GetMapping("/get/users/by/userRole/{userRole}")
    public ResponseEntity<ApiResponse<List<?>>> GetUsersByUserRole(@PathVariable UserRole userRole) {
        try {
            List<?> response = adminService.getUserByUserRole(userRole);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(), "Users Successfully Gotten", response));
        } catch (InvalidRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error Logging In", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to get Users", null));
        }
    }

    @Operation(
            summary = "Delete User By UserId REST API",
            description = "This REST API is used to Delete a User By UserId"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "HTTP Status 200 OK")
    @DeleteMapping("/delete/user/by/id/{id}")
    public ResponseEntity<ApiResponse<String>> DeleteUser(@PathVariable Long id) {
        try {
            String response = adminService.deleteUser(id);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(), "User Deleted Successfully", response));
        } catch (InvalidRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error Logging In", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to delete User", null));
        }
    }
}
