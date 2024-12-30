package com.softnet.sta.controller;

import com.softnet.sta.dto.request.RequirementRequest;
import com.softnet.sta.dto.response.ApiResponse;
import com.softnet.sta.dto.response.RequirementResponse;
import com.softnet.sta.service.interfaces.RequirementService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@Slf4j
@RequestMapping("/requirement")
public class RequirementController {
    private final RequirementService requirementService;


    @Operation(
            summary = "CREATE REQUIREMENT REST API",
            description = "This REST API is used to create new requirement"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "HTTP Status 200 OK")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "HTTP Status 400 BAD REQUEST")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "HTTP Status 500 INTERNAL SERVER ERROR")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<RequirementResponse>> createRequirement(@RequestBody RequirementRequest requirementRequest) {
        RequirementResponse createdRequirement = requirementService.createRequirement(requirementRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, "Requirement created successfully", createdRequirement));
    }



    @Operation(
            summary = "UPDATE REQUIREMENT REST API",
            description = "This REST API is UPDATE REQUIREMENT"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "HTTP Status 200 OK")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "HTTP Status 400 BAD REQUEST")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "HTTP Status 500 INTERNAL SERVER ERROR")
    @PutMapping("/update/{requirementId}")
    public ResponseEntity<ApiResponse<RequirementResponse>> updateRequirement(
            @PathVariable("requirementId") Long requirementId, @RequestBody RequirementRequest requirementRequest) {
        RequirementResponse updatedRequirement = requirementService.updateRequirement(requirementId, requirementRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(200, "Requirement updated successfully", updatedRequirement));
    }


    @Operation(
            summary = "DELETE REQUIREMENT BY ID REST API",
            description = "This REST API is used to delete requirement by ID"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "HTTP Status 200 OK")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "HTTP Status 400 BAD REQUEST")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "HTTP Status 500 INTERNAL SERVER ERROR")
    @DeleteMapping("/delete/{requirementId}")
    public ResponseEntity<ApiResponse<String>> deleteRequirement(@PathVariable("requirementId") Long requirementId) {
        requirementService.deleteRequirement(requirementId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(200, "Requirement deleted successfully", "Requirement deleted with ID: " + requirementId));
    }


}
