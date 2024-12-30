package com.softnet.sta.controller;

import com.softnet.sta.dto.response.ApiResponse;
import com.softnet.sta.dto.response.LoginResponse;
import com.softnet.sta.dto.response.SignUpResponse;
import com.softnet.sta.exception.InvalidRequestException;
import com.softnet.sta.service.interfaces.OrgRepService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Tag(
        name = "Organizational Representative Controller",
        description = "Exposes APIs to manage Organizational Representative Operations"
)

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/orgRep")
public class OrgRepController {
    private final OrgRepService orgRepService;

    @Operation(
            summary = "Upload Group Learners REST API",
            description = "This REST API is used to upload group learners"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Group Learners Uploaded Successfully")
    @PostMapping("/uploadGroupLearners")
    public ResponseEntity<ApiResponse<List<SignUpResponse>>> uploadGroupLearners(MultipartFile file) {
        List<SignUpResponse> response = orgRepService.uploadGroupLearners(file);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(), "Group Learners Uploaded successfully", response));

    }


    @Operation(
            summary = "Get Group Learners By OrgRepId REST API",
            description = "This REST API is used to get group learners by orgRepId"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Group Learners Gotten Successfully")
    @GetMapping("/get/learners/by/orgRepId/{orgRepId}")
    public ResponseEntity<ApiResponse<List<LoginResponse>>> GetUsersByOrgRepId(@PathVariable String orgRepId) {
        try {
            List<LoginResponse> response = orgRepService.getUserByOrgRepId(orgRepId);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(), "Learners Successfully Gotten", response));
        } catch (InvalidRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error Getting Learners", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to get Learners", null));
        }
    }
}
