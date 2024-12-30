package com.softnet.sta.controller;


import com.softnet.sta.dto.request.CohortRequest;
import com.softnet.sta.dto.request.CohortUpdateRequest;
import com.softnet.sta.dto.request.HeroSliderRequest;
import com.softnet.sta.dto.request.UpdateHeroSliderRequest;
import com.softnet.sta.dto.response.ApiResponse;
import com.softnet.sta.dto.response.CohortResponse;
import com.softnet.sta.dto.response.HeroSliderResponse;
import com.softnet.sta.repository.CohortRepository;
import com.softnet.sta.service.interfaces.CohortService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Cohort Controller",
        description = "Exposes APIs to manage Cohort Operations"
)

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/cohort")
public class CohortController {

    private final CohortService cohortService;


    @Operation(
            summary = "Create Cohort REST API",
            description = "This REST API is used to create new Cohort"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Cohort Created Successfully")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<List<CohortResponse>>> createCohort(@RequestBody List<CohortRequest> requests) {
        try {
            List<CohortResponse> response = cohortService.createCohort(requests);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(), "Cohort Created successfully", response));
        } catch (Exception e) {
            log.error("Error Creating Cohort", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to Create Cohort", null));
        }
    }

    @Operation(
            summary = "Get All Cohort REST API",
            description = "This REST API is used to All Cohort"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Cohort gotten Successfully")
    @GetMapping("/get/all/cohort")
    public ResponseEntity<ApiResponse<List<CohortResponse>>> getAllCohort() {
        try {
            List<CohortResponse> response = cohortService.getAllCohort();
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(), "List of Cohort Gotten successfully", response));
        } catch (Exception e) {
            log.error("Error getting List of Cohort", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to get List of Cohort", null));
        }
    }


    @Operation(
            summary = "Update Cohort by Id REST API",
            description = "This REST API is used to Update an existing Cohort by Id"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Cohort Updated Successfully")
    @PatchMapping("/update/by/id")
    public ResponseEntity<ApiResponse<List<CohortResponse>>> updateCohortById(@RequestBody List<CohortUpdateRequest> requests) {
        try {
            List<CohortResponse> response = cohortService.updateCohortById(requests);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(), "Cohort Updated successfully", response));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error updating Cohort", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to Updated Cohort", null));
        }
    }


    @Operation(
            summary = "Delete Cohort by Id REST API",
            description = "This REST API is used to Delete an existing Cohort By Id"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Cohort Deleted Successfully")
    @DeleteMapping("/delete/by/id/{id}")
    public ResponseEntity<ApiResponse<List<CohortResponse>>> deleteCohortById(@PathVariable Long id) {
        try {
            List<CohortResponse> response = cohortService.deleteCohortById(id);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.CREATED.value(), "Cohort with Id: " + id + " Deleted successfully. Remaining Cohort below", response));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (Exception e) {
            log.error("Error deleting Cohort", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to delete Cohort", null));
        }
    }

}
